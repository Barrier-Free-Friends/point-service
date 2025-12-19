package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "포인트 획득 및 사용 취소 요청 DTO")
public record PointCancelRequest(
        @Schema(description = "포인트 획득 및 사용 취소를 원하는 유저 ID", example = "3b455372-a0d5-4c9d-aaf9-347a94db7de1")
        UUID userId,

        @Schema(description = "포인트 획득 및 사용 시의 sourceId (포인트 획득은 제보 ID, 포인트 사용은 보상 ID)")
        UUID sourceId
) {
}
