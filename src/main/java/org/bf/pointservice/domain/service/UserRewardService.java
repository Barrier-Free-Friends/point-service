package org.bf.pointservice.domain.service;

import org.bf.pointservice.domain.entity.reward.Reward;

import java.util.UUID;

public interface UserRewardService {
    void grantReward(UUID userId, Reward reward);
}
