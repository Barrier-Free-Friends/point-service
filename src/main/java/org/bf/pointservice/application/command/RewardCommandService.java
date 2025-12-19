package org.bf.pointservice.application.command;

import org.bf.pointservice.application.dto.RewardCreateRequest;
import org.bf.pointservice.application.dto.RewardResponse;
import org.bf.pointservice.application.dto.RewardUpdateRequest;

import java.util.UUID;

/**
 * 보상 생성, 수정, 삭제 서비스
 * */
public interface RewardCommandService {
    /**
     * 보상 생성
     * */
    RewardResponse createReward(RewardCreateRequest request);

    /**
     * 보상 수정
     * */
    RewardResponse updateReward(UUID rewardId, RewardUpdateRequest request);

    /**
     * 보상 삭제
     * */
    void deleteReward(UUID rewardId);
}
