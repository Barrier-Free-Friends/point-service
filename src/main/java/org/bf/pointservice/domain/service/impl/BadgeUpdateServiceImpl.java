package org.bf.pointservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.repository.badge.BadgeDetailsRepository;
import org.bf.pointservice.domain.service.BadgePolicyService;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeUpdateServiceImpl implements BadgeUpdateService {

    private final BadgeDetailsRepository badgeDetailsRepository;
    private final BadgePolicyService badgePolicyService;

    /**
     * 주어진 누적 포인트룰 확인하여 가질 수 있는 뱃지 중 최소 포인트 요건이 가장 높은 뱃지로 업데이트
     * */
    @Override
    public void updateBadge(PointBalance pointBalance) {
        // 포인트 구간에 맞는 최신 뱃지 조회
        UUID newBadgeId = badgeDetailsRepository.findByPointRange(pointBalance.getTotalAccumulatedBalance())
                .map(Badge::getBadgeId)
                .orElse(null);

        log.info("Updating badge for user {}: {} -> {}", pointBalance.getUserId(), pointBalance.getCurrentBadgeId(), newBadgeId);

        // 뱃지 ID 업데이트
        pointBalance.updateBadge(newBadgeId);
        Long currentSystemVersion = badgePolicyService.getLastestVersion();
        pointBalance.updateBadgeVersion(currentSystemVersion);

        log.info("Successfully updated to policy version {}", currentSystemVersion);
    }
}
