package org.bf.pointservice.application.command.impl;

import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.global.security.SecurityUtils;
import org.bf.pointservice.application.command.RewardCommandService;
import org.bf.pointservice.application.dto.RewardCreateRequest;
import org.bf.pointservice.application.dto.RewardResponse;
import org.bf.pointservice.application.dto.RewardUpdateRequest;
import org.bf.pointservice.domain.entity.reward.Reward;
import org.bf.pointservice.domain.exception.reward.RewardErrorCode;
import org.bf.pointservice.domain.repository.reward.RewardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardCommandServiceImpl implements RewardCommandService {

    private final RewardRepository rewardRepository;
    private final SecurityUtils securityUtils;

    /**
     * 신규 보상 생성
     * */
    @Override
    public RewardResponse createReward(RewardCreateRequest request) {
        Reward reward = Reward.builder()
                .rewardName(request.rewardName())
                .price(request.price())
                .descriptions(request.descriptions())
                .stock(request.stock())
                .expiredAt(request.expiredAt())
                .build();
        return RewardResponse.from(rewardRepository.save(reward));
    }

    /**
     * 보상 정보 수정
     * */
    @Override
    public RewardResponse updateReward(UUID rewardId, RewardUpdateRequest request) {
        Reward reward = rewardRepository.findByRewardIdAndDeletedAtIsNull(rewardId).orElseThrow(() -> new CustomException(RewardErrorCode.REWARD_NOT_FOUND));
        if (request.rewardName() != null && !request.rewardName().isEmpty()) {
            reward.updateName(request.rewardName());
        }
        if (request.description() != null && !request.description().isEmpty()) {
            reward.updateDescriptions(request.description());
        }
        if (request.price() != null) {
            reward.updatePrice(request.price());
        }
        return RewardResponse.from(reward);
    }

    /**
     * 보상 삭제
     * */
    @Override
    public void deleteReward(UUID rewardId) {
        Reward reward = rewardRepository.findByRewardIdAndDeletedAtIsNull(rewardId).orElseThrow(() -> new CustomException(RewardErrorCode.REWARD_NOT_FOUND));
        reward.softDelete(securityUtils.getCurrentUsername());
    }
}
