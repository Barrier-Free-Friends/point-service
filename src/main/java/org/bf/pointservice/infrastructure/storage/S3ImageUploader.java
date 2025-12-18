package org.bf.pointservice.infrastructure.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.service.ImageUploader;
import org.bf.pointservice.infrastructure.exception.S3ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ImageUploader implements ImageUploader {

    private final AmazonS3Client amazonS3Client;
    private static final String key = "images/badge/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile file) {
        // 업로드될 파일 경로 (파일명) 생성
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = key + UUID.randomUUID().toString() + "_" + originalFilename;

        // S3 업로드 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucket,
                    uniqueFilename,
                    inputStream,
                    metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3Client.putObject(putObjectRequest);
            return amazonS3Client.getUrl(bucket, uniqueFilename).toString();
        } catch (Exception e) {
            throw new CustomException(S3ErrorCode.S3_FILE_UPLOAD_FAILED);
        }
    }
}
