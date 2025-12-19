package org.bf.pointservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.entity.reward.Reward;
import org.bf.pointservice.domain.entity.reward.UserReward;
import org.bf.pointservice.domain.repository.reward.UserRewardRepository;
import org.bf.pointservice.domain.service.UserRewardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRewardServiceImpl implements UserRewardService {

    private final UserRewardRepository userRewardRepository;

    /**
     * 사용자에게 보상 할당 (부여)
     * */
    @Override
    public void grantReward(UUID userId, Reward reward) {
        userRewardRepository.save(
                UserReward.builder()
                        .userId(userId)
                        .rewardId(reward.getRewardId())
                        .rewardName(reward.getRewardName())
                        .description(reward.getDescriptions())
                        .acquiredPrice(reward.getPrice())
                        .expiredAt(reward.getExpiredAt())
                        .build()
        );
    }
}
