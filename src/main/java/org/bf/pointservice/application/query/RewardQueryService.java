package org.bf.pointservice.application.query;

import org.bf.pointservice.application.dto.RewardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RewardQueryService {
    // 보상 목록 조회
    Page<RewardResponse> getRewards(Pageable pageable);

    // 사용자가 보유한 보상 목록 조회
    Page<RewardResponse> getRewardsFromUser(Pageable pageable);

    // 단일 보상 조회
    RewardResponse getReward(UUID rewardId);
}
