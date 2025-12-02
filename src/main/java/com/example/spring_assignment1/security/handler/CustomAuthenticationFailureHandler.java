package com.example.spring_assignment1.security.handler;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.dto.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException{

        CustomResponseCode responseCode = getResponseCode(exception);
        response.setStatus(responseCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        BaseResponse<Object> errorResponse = BaseResponse.error(responseCode);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
    private CustomResponseCode getResponseCode(AuthenticationException exception) {
        if(exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException){
            return CustomResponseCode.INVALID_CREDENTIALS;
        }
        return CustomResponseCode.INTERNAL_SERVER_ERROR;
    }
}
