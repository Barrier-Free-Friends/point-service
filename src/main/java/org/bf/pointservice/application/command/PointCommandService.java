package org.bf.pointservice.application.command;

import org.bf.pointservice.application.dto.PointGainRequest;

import java.util.UUID;

public interface PointCommandService {
    // 포인트 획득
    void gainPoint(PointGainRequest request);

    // 포인트 사용
    void usePoint(UUID rewardId);

    // 포인트 사용 및 획득 취소
    void cancel(UUID transactionId);
}
