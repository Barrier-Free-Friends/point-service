package org.bf.pointservice.domain.service;

import org.bf.pointservice.domain.entity.point.PointBalance;

/**
 * 누적 포인트에 따라 뱃지 등급 업데이트
 * */
public interface BadgeUpdateService {
    void updateBadge(PointBalance pointBalance);
}
