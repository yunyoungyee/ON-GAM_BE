package com.example.spring_assignment1.service;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.domain.Post;
import com.example.spring_assignment1.domain.User;
import com.example.spring_assignment1.dto.post.*;
import com.example.spring_assignment1.exception.BusinessException;
import com.example.spring_assignment1.repository.CommentRepository;
import com.example.spring_assignment1.repository.PostRepository;
import com.example.spring_assignment1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public List<PostSummaryResponse> getAllPosts() {
        return postRepository.findAll().stream().map(post-> {long commentCount = commentRepository.countByPostId(post.getId());
            return PostSummaryResponse.from(post, commentCount);
        }).toList();
    }

    public PostDetailResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BusinessException(CustomResponseCode.POST_NOT_FOUND));
        long commentCount = commentRepository.countByPostId(post.getId());
        post.incrementViews();
        return PostDetailResponse.from(post,commentCount);
    }

    @Transactional
    public PostResponse createPost(PostRequest req) {
        Long userId = req.getUserId();
        String nickname = userRepository.findNicknameById(userId).orElseThrow(() -> new BusinessException(CustomResponseCode.USER_NOT_FOUND)); //쿼리 1개
        User proxyAuthor = userRepository.getReferenceById(userId);
        Post post = new Post(req.getTitle(), req.getContent(), proxyAuthor); //작성자 닉네임을 보여줘야하기 때문에 필요한 객체(author) == 필요한 쿼리
        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost,nickname);
    }

    @Transactional
    public PostResponse updatePost(Long id, PostRequest req) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BusinessException(CustomResponseCode.POST_NOT_FOUND));
        if(!post.isMyPostByUserId(req.getUserId())) {
            throw new BusinessException(CustomResponseCode.FORBIDDEN_ERROR);
        }
        post.updatePost(req.getTitle(), req.getContent());
        long commentCount = commentRepository.countByPostId(post.getId());
        return PostResponse.from(post, commentCount);
    }

    @Transactional
    public void deletePost(Long id) {
        if(!postRepository.existsById(id)) {
            throw new BusinessException(CustomResponseCode.POST_NOT_FOUND);
        }
        postRepository.deleteById(id);
    }
}
