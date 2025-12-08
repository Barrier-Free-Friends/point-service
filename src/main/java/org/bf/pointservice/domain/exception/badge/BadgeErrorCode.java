package org.bf.pointservice.domain.exception.badge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bf.global.infrastructure.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BadgeErrorCode implements BaseErrorCode {
    INVALID_POINT_MINUS(HttpStatus.BAD_REQUEST, "BADGE_MIN_POINT_MINUS_400", "뱃지 포인트 요건은 음수일 수 없습니다."),
    INVALID_MIN_POINT_OVER(HttpStatus.BAD_REQUEST, "BADGE_MIN_POINT_OVER_400", "뱃지 획득을 위한 최소 포인트 요건은 뱃지 유지를 위한 최대 포인트 요건보다 클 수 없습니다."),
    INVALID_POINT_GAP(HttpStatus.BAD_REQUEST, "BADGE_GAP_400", "뱃지 유지를 위한 포인트 구간은 기존과 겹치거나, 중간이 누락되어선 안됩니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
