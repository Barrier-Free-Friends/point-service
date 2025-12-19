package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "뱃지 이미지 반환 DTO")
public record BadgeImageResponse(
        @Schema(description = "뱃지 이미지 url")
        String imgUrl
) {
}
