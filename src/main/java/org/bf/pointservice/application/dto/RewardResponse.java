package org.bf.pointservice.application.dto;

import org.bf.pointservice.domain.entity.reward.Reward;
import org.bf.pointservice.domain.entity.reward.UserReward;

import java.util.UUID;

public record RewardResponse(
        UUID rewardId,
        String rewardName,
        int price,
        String descriptions
) {
    public static RewardResponse from(Reward reward) {
        return new RewardResponse(
                reward.getRewardId(),
                reward.getRewardName(),
                reward.getPrice(),
                reward.getDescriptions()
        );
    }

    public static RewardResponse from(UserReward userReward) {
        return new RewardResponse(
                userReward.getRewardId(),
                userReward.getRewardSnapshot().getRewardName(),
                userReward.getRewardSnapshot().getAcquiredPrice(),
                userReward.getRewardSnapshot().getDescriptions()
        );
    }
}
