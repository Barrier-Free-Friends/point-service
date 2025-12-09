package org.bf.pointservice.domain.service;

import java.util.UUID;

public interface PointGainService {
    /**
     * 포인트 획득 프로세스 전체를 처리
     * 잔액, 누적 포인트, 뱃지 업데이트, History 기록 생성
     * */
    void gainPoints(UUID userId, int points, String sourceTable, UUID sourceId);
}
