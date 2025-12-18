package org.bf.pointservice.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
    // 이미지 파일 업로드
    String upload(MultipartFile file);
}
