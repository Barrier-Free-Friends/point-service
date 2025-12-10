package org.bf.pointservice.domain.exception.badge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bf.global.infrastructure.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BadgeErrorCode implements BaseErrorCode {
    INVALID_POINT_MINUS(HttpStatus.BAD_REQUEST, "BADGE_MIN_POINT_MINUS_400", "뱃지 포인트 요건은 음수일 수 없습니다."),
    INVALID_MIN_POINT(HttpStatus.BAD_REQUEST, "BADGE_MIN_POINT400", "뱃지 획득을 위한 최소 포인트 요건이 이미 존재합니다."),
    BADGE_NOT_FOUND(HttpStatus.NOT_FOUND, "BADGE400", "해당하는 뱃지를 찾을 수 없습니다."),
    INVALID_BADGE_NAME(HttpStatus.BAD_REQUEST, "BADGE_NAME400", "뱃지 이름은 중복되어선 안됩니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
