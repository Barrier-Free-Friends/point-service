package org.bf.pointservice.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bf.global.infrastructure.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PointBalanceErrorCode implements BaseErrorCode {
    INVALID_GAIN_POINT(HttpStatus.BAD_REQUEST, "GAIN_POINT_400", "획득한 포인트는 음수가 될 수 없습니다."),
    INVALID_USE_POINT(HttpStatus.BAD_REQUEST, "USE_POINT_400", "사용한 포인트는 음수가 될 수 없습니다."),
    INVALID_POINT_BALANCE(HttpStatus.BAD_REQUEST, "POINT_BALANCE_400", "현재 포인트 잔액은 음수가 될 수 없습니다."),
    INVALID_TOTAL_POINT(HttpStatus.BAD_REQUEST, "TOTAL_POINT_400", "누적 포인트는 음수가 될 수 없습니다.");;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
