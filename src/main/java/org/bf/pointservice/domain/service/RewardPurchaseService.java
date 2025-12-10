package org.bf.pointservice.domain.service;

import java.util.UUID;

public interface RewardPurchaseService {
    void purchaseReward(UUID userId, UUID rewardId);
}
