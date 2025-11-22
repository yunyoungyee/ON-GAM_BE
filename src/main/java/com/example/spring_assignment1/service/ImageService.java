package com.example.spring_assignment1.service;

import com.example.spring_assignment1.constant.CustomResponseCode;
import com.example.spring_assignment1.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.upload-dir:src/main/resources/static/images/}")
    private String uploadDir;

    public String uploadImage(MultipartFile file){
        try{
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath,  StandardCopyOption.REPLACE_EXISTING);
            return "/images/" + fileName;
        }catch(IOException e){
            throw new BusinessException(CustomResponseCode.IMAGE_MISSING);
        }
    }
}
