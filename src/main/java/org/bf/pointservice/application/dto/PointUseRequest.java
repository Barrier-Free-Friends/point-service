package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "포인트 사용 요청 DTO")
public record PointUseRequest(
        @Schema(description = "사용 포인트")
        int points,

        @Schema(description = "포인트 사용 원인 테이블", example = "p_reward")
        String sourceTable,

        @Schema(description = "포인트 사용 원인 데이터 ID (보상 ID)")
        UUID sourceId
) {
}
