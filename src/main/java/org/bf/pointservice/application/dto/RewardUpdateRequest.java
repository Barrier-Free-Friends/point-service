package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "보상 수정 요청 DTO")
public record RewardUpdateRequest(
        @Schema(description = "보상 이름 (수정)")
        String rewardName,

        @Schema(description = "보상 획득을 위한 포인트 가격 (수정)")
        Integer price,

        @Schema(description = "보상 설명 (수정)")
        String description
) {
}
