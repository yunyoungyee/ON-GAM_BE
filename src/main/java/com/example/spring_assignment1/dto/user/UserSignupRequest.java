package com.example.spring_assignment1.dto.user;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.domain.User;
import com.example.spring_assignment1.exception.BusinessException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
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
}
