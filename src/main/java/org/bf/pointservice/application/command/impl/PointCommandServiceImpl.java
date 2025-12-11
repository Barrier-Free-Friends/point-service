package org.bf.pointservice.application.command.impl;

import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.global.security.SecurityUtils;
import org.bf.pointservice.application.command.PointCommandService;
import org.bf.pointservice.application.dto.PointCancelRequest;
import org.bf.pointservice.application.dto.PointGainRequest;
import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.exception.point.PointTransactionErrorCode;
import org.bf.pointservice.domain.repository.point.PointTransactionRepository;
import org.bf.pointservice.domain.service.PointCancellationService;
import org.bf.pointservice.domain.service.PointGainService;
import org.bf.pointservice.domain.service.RewardPurchaseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointCommandServiceImpl implements PointCommandService {

    private final SecurityUtils securityUtils;
    private final PointGainService pointGainService;
    private final RewardPurchaseService rewardPurchaseService;
    private final PointCancellationService pointCancellationService;
    private final PointTransactionRepository pointTransactionRepository;

    @Override
    public void gainPoint(PointGainRequest request) {
        pointGainService.gainPoints(
                request.userId(),
                request.points(),
                request.sourceTable(),
                request.sourceId()
        );
    }

    @Override
    public void usePoint(UUID rewardId) {
        rewardPurchaseService.purchaseReward(securityUtils.getCurrentUserId(), rewardId);
    }

    @Override
    public void cancel(PointCancelRequest request) {
        PointTransaction transaction = pointTransactionRepository.findByUserIdAndSourceIdAndDeletedAtIsNull(request.userId(), request.sourceId()).orElseThrow(() -> new CustomException(PointTransactionErrorCode.POINT_TRANSACTION_NOT_FOUND));
        pointCancellationService.cancel(transaction.getTransactionId());
    }
}
