package org.bf.pointservice.application.dto;

import org.bf.pointservice.domain.entity.reward.Reward;

import java.util.UUID;

public record RewardResponse(
        UUID rewardId,
        String rewardName,
        int price,
        String description
) {
    public static RewardResponse from(Reward reward) {
        return new RewardResponse(
                reward.getRewardId(),
                reward.getRewardName(),
                reward.getPrice(),
                reward.getDescriptions()
        );
    }
}
