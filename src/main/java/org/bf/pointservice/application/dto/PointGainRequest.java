package org.bf.pointservice.application.dto;

import org.bf.pointservice.domain.event.ReportCreatedEvent;

import java.util.UUID;

public record PointGainRequest(
        UUID userId,
        int points,
        String sourceTable,
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
