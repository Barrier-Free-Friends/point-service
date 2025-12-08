package org.bf.pointservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.entity.Badge;
import org.bf.pointservice.domain.repository.BadgeDetailsRepository;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BadgeUpdateServiceImpl implements BadgeUpdateService {

    private final BadgeDetailsRepository badgeDetailsRepository;

    /**
     * 주어진 누적 포인트가 minPoint, maxPoint 구간 사이에 있는 뱃지를 탐색
     * */
    @Override
    public UUID findNewBadgeId(long totalAccumulatedBalance) {
        return badgeDetailsRepository.findByPointRange(totalAccumulatedBalance)
                .map(Badge::getBadgeId)
                .orElse(null);
    }
}
