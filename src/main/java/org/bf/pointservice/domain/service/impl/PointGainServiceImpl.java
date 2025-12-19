package org.bf.pointservice.domain.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.entity.point.Type;
import org.bf.pointservice.domain.exception.point.PointTransactionErrorCode;
import org.bf.pointservice.domain.repository.point.PointBalanceRepository;
import org.bf.pointservice.domain.repository.point.PointTransactionRepository;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.bf.pointservice.domain.service.PointGainService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PointGainServiceImpl implements PointGainService {

    private final PointBalanceRepository pointBalanceRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final BadgeUpdateService badgeUpdateService;

    /**
     * 포인트 획득
     * */
    @Override
    public void gainPoints(UUID userId, int points, String sourceTable, UUID sourceId) {
        PointBalance pointBalance = getOrCreateBalance(userId);
        log.info("current points for user {}, points {}", userId, pointBalance.getCurrentBalance());

        // 포인트 획득 (현재 잔액, 누적 포인트 업데이트)
        pointBalance.gainPoints(points);
        log.info("gain points for user {}, points {}", userId, pointBalance.getCurrentBalance());

        // 사용자 뱃지 업데이트
        badgeUpdateService.updateBadge(pointBalance);

        // 사용자 포인트 거래 내역 기록
        logHistory(userId, points, sourceTable, sourceId, pointBalance.getCurrentBalance());
        log.info("거래 내역 기록");
    }

    /**
     * 유저의 포인트 계정 반환 (아직 없을 경우 생성)
     * */
    private PointBalance getOrCreateBalance(UUID userId) {
        return pointBalanceRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseGet(() -> {
                    PointBalance newBalance = PointBalance.builder().userId(userId).build();
                    return pointBalanceRepository.save(newBalance);
                });
    }

    /**
     * 포인트 획득 거래 내역 기록
     * */
    private void logHistory(UUID userId, int points, String sourceTable, UUID sourceId, int afterBalance) {
        boolean isAlreadyProcessed = pointTransactionRepository
                .findByUserIdAndSourceTableAndSourceIdAndTypeAndDeletedAtIsNull(
                        userId, sourceTable, sourceId, Type.GAIN
                ).isPresent();
        if (isAlreadyProcessed) {
            throw new CustomException(PointTransactionErrorCode.ALREADY_PROCESSED_TRANSACTION);
        }
        PointTransaction history = PointTransaction.createOriginal(userId, sourceTable, sourceId, Type.GAIN, points, afterBalance);
        pointTransactionRepository.save(history);
    }
}
