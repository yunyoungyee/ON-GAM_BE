package com.example.spring_assignment1.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupRequest { //@RequestBody로

    @Schema(description = "사용자 이메일", example = "eric@kakao.com")
    @NotBlank
    @Email
    private final String email;

    @Schema(description = "사용자 비밀번호", example = "test1234")
    @NotBlank
    private final String password;

    @Schema(description = "사용자 닉네임", example = "eric")
    @NotBlank
    private final String nickname;

    @Schema(description = "사용자 프로필 사진", example = "https://image.kr/img.jpg")
    @NotBlank
    private final String profile_image;
}
