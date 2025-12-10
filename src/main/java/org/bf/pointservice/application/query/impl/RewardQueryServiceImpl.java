package org.bf.pointservice.application.query.impl;

import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.global.security.SecurityUtils;
import org.bf.pointservice.application.dto.RewardResponse;
import org.bf.pointservice.application.query.RewardQueryService;
import org.bf.pointservice.domain.entity.reward.Reward;
import org.bf.pointservice.domain.entity.reward.UserReward;
import org.bf.pointservice.domain.exception.reward.RewardErrorCode;
import org.bf.pointservice.domain.repository.reward.RewardRepository;
import org.bf.pointservice.domain.repository.reward.UserRewardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardQueryServiceImpl implements RewardQueryService {

    private final RewardRepository rewardRepository;
    private final UserRewardRepository userRewardRepository;
    private final SecurityUtils securityUtils;

    /**
     * 선택할 수 있는 모든 보상 목록 조회
     * */
    @Override
    public Page<RewardResponse> getRewards(Pageable pageable) {
        Page<Reward> rewards = rewardRepository.findAllByDeletedAtIsNull(pageable);
        return rewards.map(RewardResponse::from);
    }

    /**
     * 유저가 소유한 보상 목록 조회
     * */
    @Override
    public Page<RewardResponse> getRewardsFromUser(Pageable pageable) {
        Page<UserReward> rewards = userRewardRepository.findByUserId(securityUtils.getCurrentUserId(), pageable);
        return rewards.map(RewardResponse::from);
    }

    /**
     * 단일 보상 조회
     * */
    @Override
    public RewardResponse getReward(UUID rewardId) {
        Reward reward = rewardRepository.findByRewardIdAndDeletedAtIsNull(rewardId).orElseThrow(() -> new CustomException(RewardErrorCode.REWARD_NOT_FOUND));
        return RewardResponse.from(reward);
    }
}
