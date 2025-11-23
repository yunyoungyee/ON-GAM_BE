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
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Transactional
    public UserResponse signup(UserSignupRequest req, MultipartFile profileImage) {
        if(profileImage == null || profileImage.isEmpty()) {
            throw new BusinessException(CustomResponseCode.IMAGE_MISSING);
        }
        String profileImageURL = imageService.uploadImage(profileImage);
        if (userRepository.existsByEmail(req.getEmail()))
            throw new BusinessException(CustomResponseCode.DUPLICATE_EMAIL);
        if (userRepository.existsByNickname(req.getNickname()))
            throw new BusinessException(CustomResponseCode.DUPLICATE_NICKNAME);
        User user = new User(req.getEmail(), req.getPassword(), req.getNickname(), profileImageURL);
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
    public UserResponse updateProfile(Long id, UserNicknameUpdateRequest req, MultipartFile profileImage) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(CustomResponseCode.USER_NOT_FOUND));
        if(req.getNickname() != null && !req.getNickname().equals(user.getNickname())) {
            if (userRepository.existsByNickname(req.getNickname())) {
                throw new BusinessException(CustomResponseCode.DUPLICATE_NICKNAME);
            }
            user.updateNickname(req.getNickname());
        }

        if(profileImage != null && !profileImage.isEmpty()) {
            String profileImageURL = imageService.uploadImage(profileImage);
            user.updateProfileImage(profileImageURL);
        }
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
        if(!user.validateUser(id)){
            throw new BusinessException(CustomResponseCode.INVALID_PASSWORD);
        }
        user.updatePassword(req.getNewPassword());
    }
}
