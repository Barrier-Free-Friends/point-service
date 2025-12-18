package org.bf.pointservice.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bf.global.infrastructure.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ErrorCode implements BaseErrorCode {
    S3_FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE500", "s3 파일 업로드 과정에서 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}