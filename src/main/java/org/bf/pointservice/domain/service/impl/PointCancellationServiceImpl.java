package org.bf.pointservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.entity.point.Type;
import org.bf.pointservice.domain.exception.point.PointBalanceErrorCode;
import org.bf.pointservice.domain.exception.point.PointTransactionErrorCode;
import org.bf.pointservice.domain.repository.point.PointBalanceRepository;
import org.bf.pointservice.domain.repository.point.PointTransactionRepository;
import org.bf.pointservice.domain.repository.reward.UserRewardRepository;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.bf.pointservice.domain.service.PointCancellationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PointCancellationServiceImpl implements PointCancellationService {

    private final PointBalanceRepository pointBalanceRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final BadgeUpdateService badgeUpdateService;
    private final UserRewardRepository userRewardRepository;

    @Override
    public void cancel(UUID transactionId) {
        PointTransaction pointTransaction = pointTransactionRepository.findByTransactionIdAndDeletedAtIsNull(transactionId).orElseThrow(
                () -> new CustomException(PointTransactionErrorCode.POINT_TRANSACTION_NOT_FOUND)
        );

        if (pointTransaction.isCancelled()) {
            throw new CustomException(PointTransactionErrorCode.ALREADY_CANCEL);
        }

        // 거래내역 TYPE에 따라 사용취소 또는 획득취소로 분기
        if (pointTransaction.getType().equals(Type.GAIN)) {
            cancelGain(pointTransaction);
            logHistory(pointTransaction, Type.CANCEL_GAIN);
        } else if (pointTransaction.getType().equals(Type.USE)) {
            cancelUse(pointTransaction);
            logHistory(pointTransaction, Type.CANCEL_USE);
        } else {
            throw new CustomException(PointTransactionErrorCode.INVALID_CANCEL_TYPE);
        }

        // 해당 거래 건 취소 처리
        pointTransaction.cancel();
    }

    /**
     * 포인트 획득 취소
     * - 현재 포인트 잔액 차감, 누적 포인트 차감, 뱃지 업데이트
     * */
    private void cancelGain(PointTransaction pointTransaction) {
        PointBalance pointBalance = findBalance(pointTransaction.getUserId());

        // 현재 포인트 잔액 차감, 누적 포인트 차감
        pointBalance.cancelGain(pointTransaction.getAmount());

        // 뱃지 업데이트
        badgeUpdateService.updateBadge(pointBalance);
    }

    /**
     * 포인트 사용 취소
     * - 현재 포인트 잔액 복구, 획득한 보상 삭제
     * */
    private void cancelUse(PointTransaction pointTransaction) {
        PointBalance pointBalance = findBalance(pointTransaction.getUserId());

        // 현재 포인트 잔액 복구
        pointBalance.cancelUse(pointTransaction.getAmount());

        // 획득한 보상 삭제
        removeRewardFromUser(pointTransaction.getUserId(), pointTransaction.getSourceId());
    }

    /**
     * 거래 내역의 userId로 해당 유저의 현재 잔액 확인
     * */
    private PointBalance findBalance(UUID userId) {
        return pointBalanceRepository.findByUserIdAndDeletedAtIsNull(userId).orElseThrow(
                () -> new CustomException(PointBalanceErrorCode.POINT_BALANCE_NOT_FOUND)
        );
    }

    /**
     * 포인트 사용 취소 시 획득한 보상 삭제
     * */
    private void removeRewardFromUser(UUID userId, UUID rewardId) {
        userRewardRepository.findByUserIdAndRewardId(userId, rewardId).ifPresent(userRewardRepository::delete);
    }

    private void logHistory(PointTransaction pointTransaction, Type type) {
        pointTransactionRepository.save(
                PointTransaction.createCancel(
                        pointTransaction.getUserId(),
                        pointTransaction.getSourceTable(),
                        pointTransaction.getSourceId(),
                        type,
                        pointTransaction.getAmount(),
                        pointTransaction.getAfterBalance(),
                        pointTransaction.getTransactionId()
                )
        );
    }
}
