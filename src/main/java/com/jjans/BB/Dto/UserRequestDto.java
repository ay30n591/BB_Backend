package com.jjans.BB.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.core.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UserRequestDto {

    @Getter
    @Setter
    public static class SignUp {

        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        private  String userName;

        private String userImgSrc;

        private  String nickName;

        private String gender;

        private String birth;
    }

    @Getter
    @Setter
    public static class Login {
        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        @Schema(description = "이메일 주소", example = "@gmail.com")

        private String email;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Getter
    @Setter
    public static class Reissue {
//        재발행
        @NotEmpty(message = "accessToken 을 입력해주세요.")
        private String accessToken;

        @NotEmpty(message = "refreshToken 을 입력해주세요.")
        private String refreshToken;
    }

    @Getter
    @Setter
    public static class Logout {
        @NotEmpty(message = "잘못된 요청입니다.")
        private String accessToken;

        @NotEmpty(message = "잘못된 요청입니다.")
        private String refreshToken;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class RequestUserSaveDto {

        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        private  String userName;

        private  String nickName;

        private List<RequestUserSaveDto> userSaveDtoList;
        public List<RequestUserSaveDto> getuserSaveDtoList() {
            return this.userSaveDtoList;
        }
    }


    @Data
    @AllArgsConstructor
    public class SearchCondition {

        @Nullable
        private String user_name;
        @Nullable
        private String nick_name;
        @Nullable
        private String email;

    }

}
