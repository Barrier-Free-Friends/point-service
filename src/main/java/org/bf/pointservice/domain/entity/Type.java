package org.bf.pointservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Type {
    EARN("포인트 획득"), USE("포인트 사용"), CANCEL("거래 취소");

    private String description;

    Type(String description) {
        this.description = description;
    }
}
