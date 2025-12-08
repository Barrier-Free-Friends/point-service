package org.bf.pointservice.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bf.global.infrastructure.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PointTransactionErrorCode implements BaseErrorCode {
    INVALID_AMOUNT(HttpStatus.BAD_REQUEST, "AMOUNT_400", "거래된 포인트는 사용 내역에서 음수로 기록될 수 없습니다."),
    INVALID_TYPE_OF_ORIGINAL(HttpStatus.BAD_REQUEST, "TYPE400_1", "거래 내역 최초 생성 시 타입이 CANCEL이 될 수 없습니다."),
    INVALID_TYPE_OF_CANCEL(HttpStatus.BAD_REQUEST, "TYPE400_2", "거래 취소 내역 생성 시 타입이 CANCEL이어야 합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
