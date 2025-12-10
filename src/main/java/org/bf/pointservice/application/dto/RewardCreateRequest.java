package org.bf.pointservice.application.dto;

public record RewardCreateRequest(
        String rewardName,
        int price,
        String description
) {
}
