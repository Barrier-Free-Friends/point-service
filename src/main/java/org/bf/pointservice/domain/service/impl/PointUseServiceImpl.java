package org.bf.pointservice.domain.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.entity.point.Type;
import org.bf.pointservice.domain.exception.point.PointBalanceErrorCode;
import org.bf.pointservice.domain.repository.point.PointBalanceRepository;
import org.bf.pointservice.domain.repository.point.PointTransactionRepository;
import org.bf.pointservice.domain.service.PointUseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PointUseServiceImpl implements PointUseService {

    private final PointBalanceRepository pointBalanceRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Override
    public void usePoints(UUID userId, int points, String sourceTable, UUID sourceId) {
        PointBalance pointBalance = pointBalanceRepository.findByUserId(userId).orElseThrow(() -> new CustomException(PointBalanceErrorCode.POINT_BALANCE_NOT_FOUND));

        // 포인트 사용
        pointBalance.usePoints(points);

        // 포인트 사용 내역 저장
        logHistory(userId, points, sourceTable, sourceId, pointBalance.getCurrentBalance());
    }

    private void logHistory(UUID userId, int points, String sourceTable, UUID sourceId, int afterBalance) {
        pointTransactionRepository.save(PointTransaction.createOriginal(userId, sourceTable, sourceId, Type.USE, points, afterBalance));
    }
}
