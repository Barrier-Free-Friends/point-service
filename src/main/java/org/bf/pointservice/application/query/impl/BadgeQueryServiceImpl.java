package org.bf.pointservice.application.query.impl;

import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.application.dto.BadgeResponse;
import org.bf.pointservice.application.query.BadgeQueryService;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.exception.badge.BadgeErrorCode;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.bf.pointservice.domain.repository.point.PointBalanceRepository;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeQueryServiceImpl implements BadgeQueryService {

    private final BadgeRepository badgeRepository;
    private final PointBalanceRepository pointBalanceRepository;
    private final BadgeUpdateService badgeUpdateService;

    @Override
    public Page<BadgeResponse> getBadges(Pageable pageable) {
        Page<Badge> badges = badgeRepository.findAllByDeletedAtIsNull(pageable);
        return badges.map(BadgeResponse::from);
    }

    @Override
    public BadgeResponse getBadge(UUID badgeId) {
        Badge badge =  badgeRepository.findById(badgeId).orElseThrow(() ->
                new CustomException(BadgeErrorCode.BADGE_NOT_FOUND));
        return BadgeResponse.from(badge);
    }

    @Override
    public String getBadgeImage(UUID userId) {
        PointBalance pointBalance = pointBalanceRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseGet(() -> {
                    PointBalance newBalance = PointBalance.builder().userId(userId).build();
                    return pointBalanceRepository.save(newBalance);
                });
        badgeUpdateService.updateBadge(pointBalance);
        Badge badge = badgeRepository.findByBadgeIdAndDeletedAtIsNull(pointBalance.getCurrentBadgeId()).orElseThrow(
                () -> new CustomException(BadgeErrorCode.BADGE_NOT_FOUND)
        );
        return badge.getImgUrl();
    }
}
