package org.bf.pointservice.application.command;

import org.bf.pointservice.application.dto.RewardCreateRequest;
import org.bf.pointservice.application.dto.RewardResponse;
import org.bf.pointservice.application.dto.RewardUpdateRequest;

import java.util.UUID;

public interface RewardCommandService {
    // 신규 보상 생성
    RewardResponse createReward(RewardCreateRequest request);

    // 보상 내용 업데이트
    RewardResponse updateReward(UUID rewardId, RewardUpdateRequest request);

    // 보상 삭제
    void deleteReward(UUID rewardId);
}
