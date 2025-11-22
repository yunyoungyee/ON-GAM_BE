package com.example.spring_assignment1.controller;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.dto.BaseResponse;
import com.example.spring_assignment1.dto.post.*;
import com.example.spring_assignment1.service.PostService;
import com.example.spring_assignment1.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController implements PostApi {
    private final PostService postService;
    public PostController(PostService postService) { this.postService = postService; }

    @GetMapping
    public ResponseEntity<BaseResponse<List<PostSummaryResponse>>> getAllPosts() {
        return ResponseUtil.success(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostDetailResponse>> getPost(@PathVariable Long id) {
        return ResponseUtil.success(postService.getPost(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<PostResponse>> createPost(
            @Valid @RequestPart("data") PostRequest request,
            @RequestPart(value = "postImage")MultipartFile postImage) {
        return ResponseUtil.success(CustomResponseCode.CREATED, postService.createPost(request, postImage));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(@PathVariable Long id, @RequestBody PostRequest request) {
        return ResponseUtil.success(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseUtil.success(CustomResponseCode.NO_CONTENT, null);
    }
}
