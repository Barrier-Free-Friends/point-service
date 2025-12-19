package org.bf.pointservice.domain.exception.reward;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bf.global.infrastructure.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RewardErrorCode implements BaseErrorCode {
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "PRICE400", "보상 구매를 위한 포인트는 음수가 될 수 없습니다."),
    INVALID_REWARD_NAME(HttpStatus.BAD_REQUEST, "REWARD_NAME_400", "보상 이름은 필수입니다."),
    INVALID_RELATION(HttpStatus.BAD_REQUEST, "RELATION400", "유저 아이디와 보상 아이디는 필수 입니다."),
    ALREADY_USED(HttpStatus.BAD_REQUEST, "STATUS400", "이미 사용한 보상입니다."),
    REWARD_NOT_FOUND(HttpStatus.NOT_FOUND, "REWARD404", "해당하는 보상을 찾을 수 없습니다."),
    INVALID_EXPIRATION_DATE(HttpStatus.BAD_REQUEST, "EXPIRATION400", "보상 만료일자는 현재 시점보다 이전일 수 없습니다."),
    REWARD_EXPIRED(HttpStatus.BAD_REQUEST, "EXPIRED_REWARD400", "사용 기간이 만료된 보상입니다."),
    REWARD_SOLD_OUT(HttpStatus.CONFLICT, "SOLD_OUT409", "보상의 남은 재고가 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
