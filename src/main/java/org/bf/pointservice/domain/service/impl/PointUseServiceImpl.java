package org.bf.pointservice.domain.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.entity.point.Type;
import org.bf.pointservice.domain.entity.reward.Reward;
import org.bf.pointservice.domain.entity.reward.UserReward;
import org.bf.pointservice.domain.exception.point.PointBalanceErrorCode;
import org.bf.pointservice.domain.exception.reward.RewardErrorCode;
import org.bf.pointservice.domain.repository.point.PointBalanceRepository;
import org.bf.pointservice.domain.repository.point.PointTransactionRepository;
import org.bf.pointservice.domain.repository.reward.RewardRepository;
import org.bf.pointservice.domain.repository.reward.UserRewardRepository;
import org.bf.pointservice.domain.service.PointUseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PointUseServiceImpl implements PointUseService {

    private final PointBalanceRepository pointBalanceRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final UserRewardRepository userRewardRepository;
    private final RewardRepository rewardRepository;

    @Override
    public void usePoints(UUID userId, int points, String sourceTable, UUID sourceId) {
        PointBalance pointBalance = pointBalanceRepository.findByUserId(userId).orElseThrow(() -> new CustomException(PointBalanceErrorCode.POINT_BALANCE_NOT_FOUND));

        // 포인트 사용
        pointBalance.usePoints(points);

        // 포인트를 사용하여 획득한 보상 추가
        Reward reward = rewardRepository.findByRewardId(sourceId).orElseThrow(() -> new CustomException(RewardErrorCode.REWARD_NOT_FOUND));
        addReward(userId, reward);

        // 포인트 사용 내역 저장
        logHistory(userId, points, sourceTable, sourceId, pointBalance.getCurrentBalance());
    }

   private void addReward(UUID userId, Reward reward) {
       userRewardRepository.save(
               UserReward.builder()
                       .userId(userId)
                       .rewardId(reward.getRewardId())
                       .rewardName(reward.getRewardName())
                       .description(reward.getDescriptions())
                       .acquiredPrice(reward.getPrice())
                       .build()
       );
   }

    private void logHistory(UUID userId, int points, String sourceTable, UUID sourceId, int afterBalance) {
        pointTransactionRepository.save(PointTransaction.createOriginal(userId, sourceTable, sourceId, Type.USE, points, afterBalance));
    }
}
