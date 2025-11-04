package com.example.spring_assignment1.service;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.domain.Comment;
import com.example.spring_assignment1.domain.Post;
import com.example.spring_assignment1.domain.User;
import com.example.spring_assignment1.dto.comment.CommentRequest;
import com.example.spring_assignment1.dto.comment.CommentResponse;
import com.example.spring_assignment1.exception.BusinessException;
import com.example.spring_assignment1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse createComment(CommentRequest req) {
        Long userId = req.getUserId();
        Long postId = req.getPostId();
        String nickname = userRepository.findNicknameById(userId).orElseThrow(() -> new BusinessException(CustomResponseCode.USER_NOT_FOUND));
        User proxyUser = userRepository.getReferenceById(userId);
        Post proxyPost = postRepository.getReferenceById(postId);
        Comment comment = new Comment(req.getContent(), proxyPost, proxyUser);
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.from(savedComment, nickname);
    }

    @Transactional
    public List<CommentResponse> getCommentsByPost(Long postId) {
        if(!postRepository.existsById(postId)) {
            throw new BusinessException(CustomResponseCode.POST_NOT_FOUND);
        }
        List<Comment> comments = commentRepository.findALlByPostId(postId);
        return comments.stream().map(comment-> CommentResponse.from(comment,comment.getUser().getNickname())).toList();
    }

    @Transactional
    public CommentResponse updateComment(Long id, CommentRequest req) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new BusinessException(CustomResponseCode.COMMENT_NOT_FOUND));
        if(!comment.isMyCommentByUserId(req.getUserId())) {
            throw new BusinessException(CustomResponseCode.FORBIDDEN_ERROR);
        }
        comment.updateComment(req.getContent());
        return CommentResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        if(!commentRepository.existsById(id)) {
            throw new BusinessException(CustomResponseCode.COMMENT_NOT_FOUND);
        }
        commentRepository.deleteById(id);
    }
}
