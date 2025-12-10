package org.bf.pointservice.application.dto;

import org.bf.pointservice.domain.entity.badge.Badge;

import java.util.UUID;

public record BadgeResponse(
        UUID badgeId,
        String badgeName,
        long minPoint,
        String descriptions,
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
