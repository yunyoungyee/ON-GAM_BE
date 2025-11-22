package com.example.spring_assignment1.dto.post;

import com.example.spring_assignment1.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDetailResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목", example = "API 연습하기")
    private String title;

    @Schema(description = "게시글 내용", example = "API 만드는 연습을 하고 있어요. 쉽지 않네요.")
    private String content;

    @Schema(description = "좋아요 개수", example = "9")
    private int likes;

    @Schema(description = "댓글 개수", example = "99")
    private long comments;

    @Schema(description = "조회수 개수", example = "999")
    private int views;

    @Schema(description = "게시글 생성 시간", example = "2025-10-16T18:44:16.327059")
    private LocalDateTime createdAt;

    @Schema(description = "게시글 업데이트 시간", example = "2025-10-16T18:44:16.327142")
    private LocalDateTime updatedAt;

    @Schema(description = "게시글 작성자 ID", example = "1")
    private Long authorId;

    @Schema(description = "게시글 작성자 닉네임", example = "eric")
    private String authorNickname;

    @Schema(description = "게시글 이미지 URL", example = "/images/abc.png")
    private String postImageUrl;

    @Schema(description = "프로필 이미지 URL", example = "/images/abc.png")
    private String profileImageUrl;

    public static PostDetailResponse from(Post post, long commentCount) {
        return PostDetailResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likes(post.getMetaInfo().getLikes())
                .comments(commentCount)
                .views(post.getMetaInfo().getViews())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .authorId(post.getAuthor().getId())
                .authorNickname(post.getAuthor().getNickname())
                .postImageUrl(post.getPostImage())
                .profileImageUrl(post.getAuthor().getProfileImage())
                .build();
    }
}
