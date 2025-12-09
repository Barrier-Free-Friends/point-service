package org.bf.pointservice.application.dto;

import java.util.UUID;

public record PointUseRequest(
        int points,
        String sourceTable,
        UUID sourceId
) {
}
