package com.example.spring_assignment1.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String postImage;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToOne(mappedBy = "postInfo",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private PostMetaInfo metaInfo; //단일

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String title, String content, User author, String postImage) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.postImage = postImage;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        setMetaInfo(new PostMetaInfo(this));
    }
    public Post(){}

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
    public void updatePostImage(String postImage) {
        this.postImage = postImage;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isMyPostByUserId(Long currentUserId) {
        return author.getId().equals(currentUserId);
    }

    public void setMetaInfo(PostMetaInfo metaInfo) {
        this.metaInfo = metaInfo;
        if(metaInfo.getPostInfo() != this){
            metaInfo.setPostInfo(this);
        }
    }

    public void incrementLikes(){
        if(metaInfo != null){
            metaInfo.incrementLikes();
        }
    }

    public void incrementViews(){
        if(metaInfo != null){
            metaInfo.incrementViews();
        }
    }

}