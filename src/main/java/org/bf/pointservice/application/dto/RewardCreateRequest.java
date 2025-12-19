package org.bf.pointservice.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "보상 생성 요청 DTO")
public record RewardCreateRequest(
        @Schema(description = "보상 이름", example = "아메리카노 1,000원 할인 쿠폰")
        String rewardName,

        @Schema(description = "획득을 위해 사용해야 하는 포인트", example = "100")
        int price,

        @Schema(description = "보상 설명", example = "00커피에서 아메리카노 구매 시 1,000원 할인을 받을 수 있습니다.")
        String descriptions,

        @Schema(description = "재고, 미 입력 시 무제한", example = "1000")
        Integer stock,

        @Schema(description = "만료일, 미 입력 시 무제한", example = "2025-12-25 15:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime expiredAt
) {
}
