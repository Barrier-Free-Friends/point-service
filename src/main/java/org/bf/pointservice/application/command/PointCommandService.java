package org.bf.pointservice.application.command;

import org.bf.pointservice.application.dto.PointCancelRequest;
import org.bf.pointservice.application.dto.PointGainRequest;

import java.util.UUID;

/**
 * 포인트 획득, 사용, 취소 서비스
 * */
public interface PointCommandService {
    /**
     * 포인트 획득
     * */
    void gainPoint(PointGainRequest request);

    /**
     * 포인트 사용
     * */
    void usePoint(UUID rewardId);

    /**
     * 포인트 획득 및 사용 취소
     * */
    void cancel(PointCancelRequest request);
}
