package com.example.spring_assignment1.dto.comment;

import com.example.spring_assignment1.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {

    @Schema(description = "댓글 ID", example = "1")
    private Long commentId;

    @Schema(description = "댓글 내용", example = "API연습 화이팅!")
    private String content;

    @Schema(description = "댓글 생성 시간", example = "2025-10-16T18:44:16.327059")
    private LocalDateTime createdAt;

    @Schema(description = "댓글 수정 시간", example = "2025-10-16T18:44:16.327059")
    private LocalDateTime updatedAt;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "댓글 작성자 닉네임", example = "eric")
    private String commenterNickname;

    public static CommentResponse from(Comment comment, String nickname) {
        return new CommentResponse(comment.getId(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getUser().getId(), nickname);
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getUser().getId(), null);
    }
}


