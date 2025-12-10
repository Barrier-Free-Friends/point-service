package org.bf.pointservice.application.dto;

public record RewardUpdateRequest(
        String rewardName,
        Integer price,
        String description
) {
}
