package org.bf.pointservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Type {
    GAIN("포인트 획득"), USE("포인트 사용"), CANCEL_GAIN("포인트 획득 취소"), CANCEL_USE("포인트 사용 취소");

    private String description;

    Type(String description) {
        this.description = description;
    }
}
