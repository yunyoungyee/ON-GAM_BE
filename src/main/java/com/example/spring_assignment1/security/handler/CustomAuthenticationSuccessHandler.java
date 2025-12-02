package com.example.spring_assignment1.security.handler;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.domain.User;
import com.example.spring_assignment1.dto.BaseResponse;
import com.example.spring_assignment1.dto.user.UserResponse;
import com.example.spring_assignment1.security.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException{

        CustomResponseCode responseCode = CustomResponseCode.SUCCESS;
        response.setStatus(HttpServletResponse.SC_OK);
        response.setStatus(responseCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        Object principal = authentication.getPrincipal();
        User user = ((CustomUserDetails) principal).getUser();
        UserResponse userResponse = UserResponse.from(user);

        BaseResponse<UserResponse> successResponse = BaseResponse.success(responseCode, userResponse);
        objectMapper.writeValue(response.getOutputStream(),successResponse);
    };
}
