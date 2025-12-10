package org.bf.pointservice.application.dto;

import java.util.UUID;

public record BadgeUpdateRequest(
        UUID badgeId,
        String badgeName,
        Long minPoint,
        String descriptions,
        String imgUrl
) {
}
