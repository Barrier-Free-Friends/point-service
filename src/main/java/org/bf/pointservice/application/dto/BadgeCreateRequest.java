package org.bf.pointservice.application.dto;

public record BadgeCreateRequest(
        String badgeName,
        long minPoint,
        String descriptions,
        String imgUrl
) {
}
