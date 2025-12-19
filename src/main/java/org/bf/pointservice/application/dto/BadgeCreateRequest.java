package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "뱃지 생성 요청 DTO")
public record BadgeCreateRequest(
        @Schema(description = "뱃지 이름", example = "기본 뱃지")
        String badgeName,

        @Schema(description = "뱃지 획득을 위한 최소 포인트 요건", example = "0")
        long minPoint,

        @Schema(description = "뱃지 설명", example = "기본적으로 획득할 수 있는 뱃지")
        String descriptions
) {
}
