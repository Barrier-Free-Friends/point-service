package org.bf.pointservice.application.dto;

import java.time.LocalDateTime;

public record RewardCreateRequest(
        String rewardName,
        int price,
        String descriptions,
        Integer stock,
        LocalDateTime expiredAt
) {
}
