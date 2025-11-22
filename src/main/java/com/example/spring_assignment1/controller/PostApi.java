package com.example.spring_assignment1.controller;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.dto.BaseResponse;
import com.example.spring_assignment1.dto.post.*;
import com.example.spring_assignment1.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostApi {
    @Operation(summary = "전체 게시글 목록 조회", description = "전체 게시글 목록을 조회합니다.")
    @ApiResponse(responseCode = "200",description = "전체 게시글 목록 조회 성공")
    ResponseEntity<BaseResponse<List<PostSummaryResponse>>> getAllPosts();

    @Operation(summary = "게시글 조회", description = "사용자 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 게시글 에러")
    })
    ResponseEntity<BaseResponse<PostDetailResponse>> getPost(@PathVariable Long id);

    @Operation(summary = "게시글 생성", description = "게시글 제목, 게시글 내용, 사용자 ID를 넣고 게시글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 사용자 에러")

    })
    ResponseEntity<BaseResponse<PostResponse>> createPost(
            @Valid @RequestPart("data") PostRequest request,
            @RequestPart(value = "postImage") MultipartFile postImage);

    @Operation(summary = "게시글 수정", description = "게시글 제목, 게시글 내용, 사용자 ID를 넣고 게시글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "403",description = "사용자 권한 인가 실패 에러"),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 게시글 에러")
    })
    ResponseEntity<BaseResponse<PostResponse>> updatePost(@PathVariable Long id, @RequestBody PostRequest request);

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 게시글 에러")
    })
    ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable Long id);
}
