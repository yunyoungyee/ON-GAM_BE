package com.example.spring_assignment1.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequest {
    @Schema(description = "사용자 이메일", example = "eric@kakao.com")
    @Email
    private String email;
    @Schema(description = "사용자 비밀번호", example = "test1234")
    private String password;
}
