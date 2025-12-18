package org.bf.pointservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.entity.reward.Reward;
import org.bf.pointservice.domain.exception.reward.RewardErrorCode;
import org.bf.pointservice.domain.repository.reward.RewardRepository;
import org.bf.pointservice.domain.service.PointUseService;
import org.bf.pointservice.domain.service.RewardPurchaseService;
import org.bf.pointservice.domain.service.UserRewardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardPurchaseServiceImpl implements RewardPurchaseService {

    private final UserRewardService userRewardService;
    private final PointUseService pointUseService;
    private final RewardRepository rewardRepository;

    @Override
    public void purchaseReward(UUID userId, UUID rewardId) {
        Reward reward = rewardRepository.findByRewardIdAndDeletedAtIsNull(rewardId).orElseThrow(() -> new CustomException(RewardErrorCode.REWARD_NOT_FOUND));
        if (reward.getExpiredAt() != null && reward.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(RewardErrorCode.REWARD_EXPIRED);
        }
        reward.decreaseStock();
        pointUseService.usePoints(userId, reward.getPrice(), "p_reward", rewardId);
        userRewardService.grantReward(userId, reward);
    }
}
