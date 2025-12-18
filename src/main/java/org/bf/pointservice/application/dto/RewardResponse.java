package org.bf.pointservice.application.dto;

import org.bf.pointservice.domain.entity.reward.Reward;
import org.bf.pointservice.domain.entity.reward.UserReward;

import java.time.LocalDateTime;
import java.util.UUID;

public record RewardResponse(
        UUID rewardId,
        String rewardName,
        int price,
        String descriptions,
        Integer stock,
        LocalDateTime expiredAt
) {
    public static RewardResponse from(Reward reward) {
        return new RewardResponse(
                reward.getRewardId(),
                reward.getRewardName(),
                reward.getPrice(),
                reward.getDescriptions(),
                reward.getStock(),
                reward.getExpiredAt()
        );
    }

    public static RewardResponse from(UserReward userReward) {
        return new RewardResponse(
                userReward.getRewardId(),
                userReward.getRewardSnapshot().getRewardName(),
                userReward.getRewardSnapshot().getAcquiredPrice(),
                userReward.getRewardSnapshot().getDescriptions(),
                null,
                userReward.getRewardSnapshot().getExpiredAt()
        );
    }
}
