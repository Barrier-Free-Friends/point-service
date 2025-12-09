package org.bf.pointservice.application.command;

import java.util.UUID;

public interface PointCommandService {
    // 포인트 획득
    void gainPoint(int points, String sourceTable, UUID sourceId);

    // 포인트 사용
    void usePoint(int points, String sourceTable, UUID sourceId);

    // 포인트 사용 및 획득 취소
    void cancel(UUID transactionId);
}
