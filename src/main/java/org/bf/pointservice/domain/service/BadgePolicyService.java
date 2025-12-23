package org.bf.pointservice.domain.service;

import org.bf.pointservice.domain.entity.badge.Badge;

import java.util.UUID;

public interface BadgePolicyService {
    // 뱃지 버전 업데이트
    void updateVersion();

    // 뱃지 정보 조회
    Badge getBadgeFromCache(UUID badgeId);

    // 뱃지 버전 조회
    Long getLastestVersion();
}
