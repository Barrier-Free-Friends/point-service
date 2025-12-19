package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.bf.pointservice.domain.entity.reward.Reward;
import org.bf.pointservice.domain.entity.reward.UserReward;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "보상 반환 DTO")
public record RewardResponse(
        @Schema(description = "보상 ID")
        UUID rewardId,

        @Schema(description = "보상 이름")
        String rewardName,

        @Schema(description = "보상 획득을 위한 포인트 가격")
        int price,

        @Schema(description = "보상 설명")
        String descriptions,

        @Schema(description = "보상 재고, null일 경우 무제한")
        Integer stock,

        @Schema(description = "만료일, null일 경우 무제한")
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
