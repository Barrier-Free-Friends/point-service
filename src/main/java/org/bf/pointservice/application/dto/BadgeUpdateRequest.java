package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "뱃지 수정 요청 DTO")
public record BadgeUpdateRequest(
        @Schema(description = "뱃지 이름", example = "기본 뱃지 (수정)")
        String badgeName,

        @Schema(description = "뱃지 획득을 위한 최소 포인트 요건", example = "10")
        Long minPoint,

        @Schema(description = "뱃지 설명", example = "10포인트 획득 시 소유 가능한 뱃지")
        String descriptions
) {
}
