package com.example.spring_assignment1.security.filter;

import com.example.spring_assignment1.dto.auth.UserLoginRequest;
import com.example.spring_assignment1.security.handler.CustomAuthenticationFailureHandler;
import com.example.spring_assignment1.security.handler.CustomAuthenticationSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    public CustomLoginFilter(AuthenticationManager authenticationManager,
                             CustomAuthenticationSuccessHandler successHandler,
                             CustomAuthenticationFailureHandler failureHandler) {
        super.setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/users/auth");

        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);

        setSecurityContextRepository(securityContextRepository);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        try{
            UserLoginRequest userLoginRequest = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);
            UsernamePasswordAuthenticationToken authToken = new  UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());
            return this.getAuthenticationManager().authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
