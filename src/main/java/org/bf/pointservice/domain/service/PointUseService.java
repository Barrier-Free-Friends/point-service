package org.bf.pointservice.domain.service;

import java.util.UUID;

public interface PointUseService {
    /**
     * 포인트 사용 프로세스 전체를 처리
     * TODO: 잔액 차감, 보상 재고 감소, History 기록 생성
     */
    void usePoints(UUID userId, int points, String sourceTable, UUID sourceId);
}
