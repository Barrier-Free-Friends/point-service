package org.bf.pointservice.domain.service;

import java.util.UUID;

/**
 * 누적 포인트에 따라 뱃지 등급 업데이트
 * */
public interface BadgeUpdateService {
    UUID findNewBadgeId(long totalAccumulatedBalance);
}
