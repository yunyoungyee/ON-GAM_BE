package com.example.spring_assignment1.controller;

import com.example.spring_assignment1.dto.BaseResponse;
import com.example.spring_assignment1.dto.auth.UserLoginRequest;
import com.example.spring_assignment1.dto.user.UserPasswordUpdateRequest;
import com.example.spring_assignment1.dto.user.UserResponse;
import com.example.spring_assignment1.dto.user.UserSignupRequest;
import com.example.spring_assignment1.dto.user.UserNicknameUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UserApi {
    @Operation(summary = "회원가입", description = "이메일, 닉네임, 비밀번호, 프로필 사진을 넣고 회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "회원가입 성공"),
            @ApiResponse(responseCode = "409",description = "이메일 또는 닉네임 중복 에러")
    })
    ResponseEntity<BaseResponse<UserResponse>> signup(@Valid @RequestBody UserSignupRequest request);

    @Operation(summary = "로그인", description = "이메일, 비밀번호를 넣고 로그인을 시도합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "로그인 성공"),
            @ApiResponse(responseCode = "400",description = "올바르지 않은 비밀번호 에러"),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 사용자 에러")
    })
    ResponseEntity<BaseResponse<UserResponse>> login(@RequestBody UserLoginRequest request);

    @Operation(summary = "로그아웃", description = "로그아웃을 시도합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "로그아웃 성공"),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 사용자 에러")
    })
    ResponseEntity<BaseResponse<Void>> logout(@RequestParam Long userId);

    @Operation(summary = "닉네임 수정", description = "새로운 닉네임으로 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "수정 성공"),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 사용자 에러"),
            @ApiResponse(responseCode = "409",description = "닉네임 중복 에러")
    })
    ResponseEntity<BaseResponse<UserResponse>> updateNickname(@PathVariable Long id, @RequestBody UserNicknameUpdateRequest request);

    @Operation(summary = "회원 탈퇴", description = "회원 정보를 지우고 탈퇴합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "탈퇴 성공"),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 사용자 에러")
    })
    ResponseEntity<BaseResponse<Void>> deleteUser(@PathVariable Long id);

    @Operation(summary = "비밀번호 수정", description = "새로운 비밀번호로 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "수정 성공"),
            @ApiResponse(responseCode = "400",description = "올바르지 않은 비밀번호 에러"),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 사용자 에러")
    })
    ResponseEntity<BaseResponse<Void>> updatePassword(@PathVariable Long id, @RequestBody UserPasswordUpdateRequest request);
}
