package org.bf.pointservice.application.dto;

import java.util.UUID;

public record PointCancelRequest(
        UUID userId,
        UUID sourceId
) {
}
