package org.bf.pointservice.domain.service;

import java.util.UUID;

/**
 * 보상 구매 서비스
 * */
public interface RewardPurchaseService {
    void purchaseReward(UUID userId, UUID rewardId);
}
