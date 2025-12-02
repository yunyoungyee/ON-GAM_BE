package com.example.spring_assignment1.controller;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.dto.BaseResponse;
import com.example.spring_assignment1.dto.auth.UserLoginRequest;
import com.example.spring_assignment1.dto.user.UserPasswordUpdateRequest;
import com.example.spring_assignment1.dto.user.UserResponse;
import com.example.spring_assignment1.dto.user.UserSignupRequest;
import com.example.spring_assignment1.dto.user.UserNicknameUpdateRequest;
import com.example.spring_assignment1.security.CustomUserDetails;
import com.example.spring_assignment1.service.UserService;
import com.example.spring_assignment1.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController implements UserApi {
    private UserService userService;
    //생성자가 하나라서 Autowired 자동 적용
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<UserResponse>> signup(
            @Valid @RequestPart("data") UserSignupRequest request,
            @RequestPart(value = "profileImage") MultipartFile profileImage){
        return ResponseUtil.success(CustomResponseCode.CREATED, userService.signup(request, profileImage));
    }

    @PostMapping("/auth")
    public ResponseEntity<BaseResponse<UserResponse>> login(@RequestBody UserLoginRequest request) {
        return ResponseUtil.success(userService.login(request));
    }

    @PostMapping("/auth/token")
    public ResponseEntity<BaseResponse<Void>> logout(@RequestParam Long userId) {
        userService.logout(userId);
        return ResponseUtil.success(CustomResponseCode.NO_CONTENT, null);
    }

    @PatchMapping(value = "/{id}/nickname",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<UserResponse>> updateProfile(
            @PathVariable Long id,
            @Valid @RequestPart("data") UserNicknameUpdateRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        return ResponseUtil.success(userService.updateProfile(id, request, profileImage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseUtil.success(CustomResponseCode.NO_CONTENT, null);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<BaseResponse<Void>> updatePassword(
            @PathVariable Long id, @RequestBody UserPasswordUpdateRequest request) {
        userService.updatePassword(id, request);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        if (!userDetails.getUser().getId().equals(id)) {
            return ResponseUtil.error(CustomResponseCode.FORBIDDEN_ERROR);
        }
        return ResponseUtil.success(CustomResponseCode.NO_CONTENT, null);
    }



}
