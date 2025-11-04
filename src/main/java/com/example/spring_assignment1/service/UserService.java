package com.example.spring_assignment1.service;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.domain.User;
import com.example.spring_assignment1.dto.auth.UserLoginRequest;
import com.example.spring_assignment1.dto.user.UserPasswordUpdateRequest;
import com.example.spring_assignment1.dto.user.UserResponse;
import com.example.spring_assignment1.dto.user.UserSignupRequest;
import com.example.spring_assignment1.dto.user.UserNicknameUpdateRequest;
import com.example.spring_assignment1.exception.BusinessException;

import com.example.spring_assignment1.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse signup(UserSignupRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new BusinessException(CustomResponseCode.DUPLICATE_EMAIL);
        if (userRepository.existsByNickname(req.getNickname()))
            throw new BusinessException(CustomResponseCode.DUPLICATE_NICKNAME);
        User user = new User(req.getEmail(), req.getPassword(), req.getNickname(), req.getProfile_image());
        return UserResponse.from(userRepository.save(user));
    }

    @Transactional(readOnly=true)
    public UserResponse login(UserLoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new BusinessException(CustomResponseCode.USER_NOT_FOUND));
        if (!user.getPassword().equals(req.getPassword()))
            throw new BusinessException(CustomResponseCode.INVALID_PASSWORD);
        return UserResponse.from(user);
    }

    @Transactional(readOnly=true)
    public void logout(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new BusinessException(CustomResponseCode.USER_NOT_FOUND));
    }

    @Transactional
    public UserResponse updateNickname(Long id, UserNicknameUpdateRequest req) {
        if (userRepository.existsByNickname(req.getNickname()))
            throw new BusinessException(CustomResponseCode.DUPLICATE_NICKNAME);
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(CustomResponseCode.USER_NOT_FOUND));
        user.updateNickname(req.getNickname());
        return UserResponse.from(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new BusinessException(CustomResponseCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void updatePassword(Long id, UserPasswordUpdateRequest req) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(CustomResponseCode.USER_NOT_FOUND));
        if(!user.validatePassword(req.getCurrentPassword())){
            throw new BusinessException(CustomResponseCode.INVALID_PASSWORD);
        }
        user.updatePassword(req.getNewPassword());
    }
}
