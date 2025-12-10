package org.bf.pointservice.domain.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.entity.point.Type;
import org.bf.pointservice.domain.repository.point.PointBalanceRepository;
import org.bf.pointservice.domain.repository.point.PointTransactionRepository;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.bf.pointservice.domain.service.PointGainService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PointGainServiceImpl implements PointGainService {

    private final PointBalanceRepository pointBalanceRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final BadgeUpdateService badgeUpdateService;

    @Override
    public void gainPoints(UUID userId, int points, String sourceTable, UUID sourceId) {
        PointBalance pointBalance = getOrCreateBalance(userId);

        // 포인트 획득 (현재 잔액, 누적 포인트 업데이트)
        pointBalance.gainPoints(points);

        // 사용자 뱃지 업데이트
        UUID badgeId = badgeUpdateService.findNewBadgeId(pointBalance.getTotalAccumulatedBalance());
        pointBalance.updateBadge(badgeId);

        // 사용자 포인트 거래 내역 기록
        logHistory(userId, points, sourceTable, sourceId, pointBalance.getCurrentBalance());
    }

    private PointBalance getOrCreateBalance(UUID userId) {
        return pointBalanceRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseGet(() -> {
                    PointBalance newBalance = PointBalance.builder().userId(userId).build();
                    return pointBalanceRepository.save(newBalance);
                });
    }

    private void logHistory(UUID userId, int points, String sourceTable, UUID sourceId, int afterBalance) {
        PointTransaction history = PointTransaction.createOriginal(userId, sourceTable, sourceId, Type.GAIN, points, afterBalance);
        pointTransactionRepository.save(history);
    }
}
