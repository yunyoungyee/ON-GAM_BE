package com.example.spring_assignment1.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PostMetaInfo {

    @Id
    private Long Id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "post_id")
    private Post postInfo;

    private int likes = 0;
    private int views = 0;

    public PostMetaInfo(Post post) {
        this.postInfo = post;
    }

    public PostMetaInfo(){}
    public void incrementLikes(){
        this.likes++;
    }
    public void incrementViews(){
        this.views++;
    }

    public void setPostInfo(Post postInfo) {
        this.postInfo = postInfo;
        if(postInfo.getMetaInfo() != this){
            postInfo.setMetaInfo(this);
        }
    }
}
