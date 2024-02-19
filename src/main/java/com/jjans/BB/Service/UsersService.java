package com.jjans.BB.Service;

import com.jjans.BB.Dto.UserRequestDto;
import com.jjans.BB.Dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UsersService {
    ResponseEntity<?> signUp(UserRequestDto.SignUp signUp);
    ResponseEntity<?> login(UserRequestDto.Login login);
    ResponseEntity<?> reissue(UserRequestDto.Reissue reissue);
    ResponseEntity<?> logout(UserRequestDto.Logout logout);
    ResponseEntity<?> userImageUpdate(MultipartFile imageFile);
    UserResponseDto.UserInfoDto userInfo();
    UserResponseDto.UserInfoDto userInfo(String nickName);

    ResponseEntity<?>  userUpdate(UserRequestDto.InfoUpdate userUpdate, MultipartFile imageFile);
    ResponseEntity<?> authority();
    ResponseEntity<?> getAllUsers();
    ResponseEntity<?> deleteUsers();

}
