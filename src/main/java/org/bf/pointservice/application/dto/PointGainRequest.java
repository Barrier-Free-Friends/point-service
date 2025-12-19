package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.bf.global.infrastructure.event.ReportCreatedEvent;

import java.util.UUID;

@Schema(description = "포인트 획득 요청 DTO")
public record PointGainRequest(
        @Schema(description = "포인트 획득을 요청하는 유저 ID", example = "3b455372-a0d5-4c9d-aaf9-347a94db7de1")
        UUID userId,

        @Schema(description = "획득 포인트", example = "10")
        int points,

        @Schema(description = "포인트 획득의 원인이 된 테이블", example = "p_report")
        String sourceTable,

        @Schema(description = "포인트 획득의 원인이 된 sourceId(제보 ID)")
        UUID sourceId
) {
    public static PointGainRequest from(ReportCreatedEvent event) {
        return new PointGainRequest(
                event.getUserId(),
                event.getPoint(),
                event.getSourceTable(),
                event.getSourceId()
        );
    }
}
