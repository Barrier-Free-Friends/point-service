package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.entity.point.PointBalance;

import java.util.UUID;

@Schema(description = "현재 포인트 잔액 반환 DTO")
public record PointBalanceResponse(
        @Schema(description = "유저 ID")
        UUID userId,

        @Schema(description = "현재 포인트 잔액")
        int currentBalance,

        @Schema(description = "누적 포인트")
        long totalAccumulatedBalance,

        @Schema(description = "현재 소유한 뱃지 ID")
        UUID currentBadgeId,

        @Schema(description = "현재 소유한 뱃지 이름")
        String badgeName,

        @Schema(description = "현재 소유한 뱃지 이미지")
        String badgeImgUrl
) {
    public static PointBalanceResponse from(PointBalance pointBalance, Badge badge) {
        return new PointBalanceResponse(
                pointBalance.getUserId(),
                pointBalance.getCurrentBalance(),
                pointBalance.getTotalAccumulatedBalance(),
                badge.getBadgeId(),
                badge.getBadgeName(),
                badge.getImgUrl()
        );
    }

    public static PointBalanceResponse fromZeroPointUser(UUID userId) {
        return new PointBalanceResponse(
                userId,
                0,
                0,
                null,
                null,
                null
        );
    }
}
