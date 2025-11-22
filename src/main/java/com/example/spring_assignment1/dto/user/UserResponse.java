package com.example.spring_assignment1.dto.user;

import com.example.spring_assignment1.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    @Schema(description = "사용자 ID", example = "1")
    private final Long id;

    @Schema(description = "사용자 이메일", example = "eric@kakao.com")
    private final String email;

    @Schema(description = "사용자 닉네임", example = "eric")
    private final String nickname;

    @Schema(description = "프로필 이미지 URL", example = "/images/~~.png")
    private final String profileImageUrl;
    public static UserResponse from(User user){
        return new UserResponse(user.getId(), user.getEmail(), user.getNickname(), user.getProfileImage());
    }
}
