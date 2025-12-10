package org.bf.pointservice.application.dto;

import java.util.UUID;

public record PointGainRequest(
        int points,
        String sourceTable,
        UUID sourceId
) {
}
