package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.bf.pointservice.domain.entity.badge.Badge;

import java.util.UUID;

@Schema(description = "뱃지 정보 반환 DTO")
public record BadgeResponse(
        @Schema(description = "뱃지 ID")
        UUID badgeId,

        @Schema(description = "뱃지 이름")
        String badgeName,

        @Schema(description = "뱃지 획득을 위한 최소 포인트 요건")
        long minPoint,

        @Schema(description = "뱃지 설명")
        String descriptions,

        @Schema(description = "뱃지 이미지")
        String imgUrl
) {
    public static BadgeResponse from(Badge badge) {
        return new BadgeResponse(
                badge.getBadgeId(),
                badge.getBadgeName(),
                badge.getMinPoint(),
                badge.getDescriptions(),
                badge.getImgUrl()
        );
    }
}
