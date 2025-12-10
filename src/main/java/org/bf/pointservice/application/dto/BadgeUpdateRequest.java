package org.bf.pointservice.application.dto;

public record BadgeUpdateRequest(
        String badgeName,
        Long minPoint,
        String descriptions,
        String imgUrl
) {
}
