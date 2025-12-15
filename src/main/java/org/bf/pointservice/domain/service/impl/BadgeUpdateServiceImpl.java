package org.bf.pointservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.repository.badge.BadgeDetailsRepository;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BadgeUpdateServiceImpl implements BadgeUpdateService {

    private final BadgeDetailsRepository badgeDetailsRepository;

    /**
     * 주어진 누적 포인트룰 확인하여 가질 수 있는 뱃지 중 최소 포인트 요건이 가장 높은 뱃지로 업데이트
     * */
    @Override
    public void updateBadge(PointBalance pointBalance) {
        UUID newBadgeId = badgeDetailsRepository.findByPointRange(pointBalance.getTotalAccumulatedBalance())
                .map(Badge::getBadgeId)
                .orElse(null);

        pointBalance.updateBadge(newBadgeId);
    }
}
