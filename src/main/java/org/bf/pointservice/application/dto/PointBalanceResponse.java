package org.bf.pointservice.application.dto;

import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.entity.point.PointBalance;

import java.util.UUID;

public record PointBalanceResponse(
        UUID userId,
        int currentBalance,
        long totalAccumulatedBalance,
        UUID currentBadgeId,
        String badgeName,
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
