package org.bf.pointservice.domain.service;

import java.util.UUID;

/**
 * 포인트 사용 및 획득 취소 처리
 * */
public interface PointCancellationService {
    /**
     * 단일 진입점: 트랜잭션 ID를 분석하여 적절한 취소 로직을 실행
     */
    void cancel(UUID transactionId);
}
