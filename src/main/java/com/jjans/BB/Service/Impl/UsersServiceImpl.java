package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Security.JwtTokenProvider;
import com.jjans.BB.Config.Utill.S3Uploader;
import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Dto.Response;
import com.jjans.BB.Dto.UserRequestDto;
import com.jjans.BB.Dto.UserResponseDto;
import com.jjans.BB.Dto.UserResponseDto.UserInfoDto;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Enum.AuthProvider;
import com.jjans.BB.Enum.Role;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.CustomUserDetailsService;
import com.jjans.BB.Service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {

    @Autowired
    private S3Uploader s3Uploader;
    private final UsersRepository usersRepository;
    private final Response response;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CustomUserDetailsService customUserDetailsService;



    @Override
    public ResponseEntity<?> signUp(UserRequestDto.SignUp signUp) {
        if (usersRepository.existsByEmail(signUp.getEmail())) {
            return response.fail("이미 회원가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        Users user = Users.builder()
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .userName(signUp.getUserName())
                .userImgSrc(signUp.getUserImgSrc())
                .gender(signUp.getGender())
                .birth(signUp.getBirth())
                .nickName(signUp.getNickName())
                .authProvider(AuthProvider.LOCAL) // 로컬 회원가입인 경우
                .role(Role.ROLE_USER) // 일반 사용자 권한 부여
                .build();
        usersRepository.save(user);

        addAdminRoleToUser(signUp.getEmail());

        return response.success("회원가입에 성공했습니다.");
    }
    // admin 권한을 사용자에게 추가하는 메소드
    private void addAdminRoleToUser(String email) {
        // 사용자의 이메일을 기반으로 데이터베이스에서 사용자를 찾아온다.
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일이 없습니다: " + email));

        // 사용자에게 admin 권한을 부여
        user.addRole(Role.ROLE_ADMIN.name());

        // 사용자 정보를 업데이트
        usersRepository.save(user);
    }
    @Override
    public ResponseEntity<?> login(UserRequestDto.Login login) {
        Users user = usersRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 존재하지 않습니다."));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return response.fail("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        } else {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(login.getEmail());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
            tokenInfo.withUserInfo(user);

            System.out.println(tokenInfo.getAccessToken());
            System.out.println(tokenInfo.getRefreshToken());

            redisTemplate.opsForValue()
                    .set("RT:" + userDetails.getUsername(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

            return response.success(tokenInfo, "로그인에 성공했습니다.", HttpStatus.OK);
        }
    }
    @Override
    public ResponseEntity<?> reissue(UserRequestDto.Reissue reissue) {
        // 토큰재발급
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());

        if (StringUtils.isEmpty(refreshToken) || !refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> logout(UserRequestDto.Logout logout) {
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());
        String refreshTokenKey = "RT:" + authentication.getName();

        if (redisTemplate.hasKey(refreshTokenKey)) {
            redisTemplate.delete(refreshTokenKey);
        }

        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue().set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success("로그아웃 되었습니다.");
    }

    @Override
    public ResponseEntity<?> userImageUpdate(MultipartFile imageFile) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        if (user.getUserImgSrc() != null){
            String dirName = "user/";
            int lastSlashIndex = user.getUserImgSrc().lastIndexOf('/');
            String fullPath = dirName + user.getUserImgSrc().substring(lastSlashIndex + 1);

            log.info("S3 bucket에서 File {} 삭제.",fullPath);
            // s3Uploader.delete(fullPath);
        }

        String imageFileUrl = null;
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                imageFileUrl = s3Uploader.upload(imageFile, "user");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 이미지 저장에 실패한 경우 예외 처리
            throw new RuntimeException("Failed to save image.");
        }
        user.setUserImgSrc(imageFileUrl);
        usersRepository.save(user);

        return response.success();
    }
    @Override
    public UserInfoDto userInfo() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        UserInfoDto userInfo = new UserInfoDto(user);

        return userInfo;
    }
    @Override
    public UserInfoDto userInfo(String nickName) {
        Users user = usersRepository.findByNickName(nickName)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        UserInfoDto userInfo = new UserInfoDto(user);

        return userInfo;
    }

    @Override
    public ResponseEntity<?>  userUpdate(UserRequestDto.InfoUpdate userUpdate, MultipartFile imageFile) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        user.setUserName(userUpdate.getUserName());
        user.setBirth(userUpdate.getBirth());
        user.setGender(userUpdate.getGender());
        user.setNickName(userUpdate.getNickName());

        String imageFileUrl = null;
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                imageFileUrl = s3Uploader.upload(imageFile, "user");
                user.setUserImgSrc(imageFileUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 이미지 저장에 실패한 경우 예외 처리
            throw new RuntimeException("Failed to save image.");
        }
        usersRepository.save(user);

        UserInfoDto userInfo = new UserInfoDto(user);
        return response.success(userInfo, "사용자 정보를 성공적으로 수정했습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> authority() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));
        user.getRoles().add(Role.ROLE_ADMIN.name());
        usersRepository.save(user);
        return response.success();
    }
    @Override
    public ResponseEntity<?> getAllUsers() {
        List<Users> allUsers = usersRepository.findAll();
        return response.success(allUsers, "모든 사용자 정보를 성공적으로 가져왔습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUsers() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 이메일을 가진 사용자가 존재하지 않습니다."));

        usersRepository.deleteById(user.getId()); // 사용자 삭제
        return response.success(user,"사용자를 성공적으로 삭제했습니다.", HttpStatus.OK);

    }
}
