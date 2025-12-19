package org.bf.pointservice.domain.service;

import org.bf.pointservice.domain.entity.reward.Reward;

import java.util.UUID;

public interface UserRewardService {

    /**
     * 보상 부여 서비스
     * */
    void grantReward(UUID userId, Reward reward);
}
