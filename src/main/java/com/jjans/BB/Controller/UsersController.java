package com.jjans.BB.Controller;


import com.jjans.BB.Config.Security.JwtTokenProvider;
import com.jjans.BB.Dto.Response;
import com.jjans.BB.Dto.UserRequestDto;
import com.jjans.BB.Service.Helper;
import com.jjans.BB.Service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.jjans.BB.Dto.UserResponseDto.UserInfoDto;


@RestController
@Validated
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;
    private final Response response;

    @GetMapping("/all")
    @Operation(summary = "모든 유저 가져오기", description = "모든 유저 가져오기")

    public ResponseEntity<?> getAllUsers() {
        return usersService.getAllUsers();
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/info")
    @Operation(summary = "유저 정보 가져오기", description = "유저 정보 가져오기")
    public ResponseEntity<?> getUserInfo() {
        UserInfoDto userInfo = usersService.userInfo();
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/info/{nickName}")
    @Operation(summary = "닉네임으로 유저 정보 가져오기", description = "닉네임으로 유저 정보 가져오기")
    public ResponseEntity<?> getUserInfoByNickname(@PathVariable String nickName) {
        UserInfoDto userInfo = usersService.userInfo(nickName);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "유저 프로필 사진 저장", description = "유저 프로필 사진 저장")
    @PostMapping(value = "/updateImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam("imageFile") MultipartFile imageFile) {
        return usersService.userImageUpdate(imageFile);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "회원가입")
    public ResponseEntity<?> signUp(@RequestBody @Validated UserRequestDto.SignUp signUp, Errors errors) {
            // validation check
        System.out.println(signUp.getEmail());
        System.out.println(signUp.getPassword());
        System.out.println(signUp.getNickName());

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineError(errors));
        }
        return usersService.signUp(signUp);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    public ResponseEntity<?> login(@RequestBody @Validated UserRequestDto.Login login, Errors errors) {
        // validation check
        System.out.println(login.getEmail());
        System.out.println(login.getPassword());

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineError(errors));
        }
        return usersService.login(login);
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "토큰 재발급")
    public ResponseEntity<?> reissue(@RequestBody @Validated UserRequestDto.Reissue reissue, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineError(errors));
        }
        return usersService.reissue(reissue);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃")
    public ResponseEntity<?> logout(@RequestBody(required = false) @Validated UserRequestDto.Logout logout, Errors errors) {
        // validation check
        if (logout == null) {
            // 요청 바디가 누락된 경우
            return response.fail("요청 바디가 누락되었습니다.", HttpStatus.BAD_REQUEST);
        }

        System.out.println(logout.getAccessToken());
        System.out.println(logout.getRefreshToken());

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineError(errors));
        }
        return usersService.logout(logout);
    }

//    @GetMapping("/authority")
//    public ResponseEntity<?> authority() {
//        log.info("ADD ROLE_ADMIN");
//        return usersService.authority();
//    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "유저 삭제", description = "유저 삭제")
    @DeleteMapping(value = "/deleteUser")
    public  ResponseEntity<?> deleteUser(){
       return usersService.deleteUsers();
    }
}