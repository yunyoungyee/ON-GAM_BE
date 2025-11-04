package com.example.spring_assignment1.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRequest {

    @Schema(description = "게시글 제목", example = "API 연습하기")
    private String title;

    @Schema(description = "게시글 내용", example = "API 만드는 연습을 하고 있어요. 쉽지 않네요.")
    private String content;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
}


