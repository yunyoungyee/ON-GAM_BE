package com.example.spring_assignment1.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserNicknameUpdateRequest {
    @Schema(description = "사용자 닉네임", example = "eric")
    private String nickname;
}
