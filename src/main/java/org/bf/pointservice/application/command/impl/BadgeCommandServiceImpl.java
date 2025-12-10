package org.bf.pointservice.application.command.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.global.security.SecurityUtils;
import org.bf.pointservice.application.command.BadgeCommandService;
import org.bf.pointservice.application.dto.BadgeCreateRequest;
import org.bf.pointservice.application.dto.BadgeResponse;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.exception.badge.BadgeErrorCode;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.bf.pointservice.domain.service.CheckPointGap;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeCommandServiceImpl implements BadgeCommandService {

    private final BadgeRepository badgeRepository;
    private final CheckPointGap checkPointGap;
    private final SecurityUtils securityUtils;

    @Override
    public BadgeResponse createBadge(BadgeCreateRequest request) {
        Badge badge = Badge.builder()
                .badgeName(request.badgeName())
                .minPoint(request.minPoint())
                .descriptions(request.descriptions())
                .imgUrl(request.imgUrl())
                .checkPointGap(checkPointGap)
                .build();
        badgeRepository.save(badge);
        return BadgeResponse.from(badge);
    }

    @Override
    public void deleteBadge(UUID badgeId) {
        Badge badge = badgeRepository.findByBadgeId(badgeId).orElseThrow(
                () -> new CustomException(BadgeErrorCode.BADGE_NOT_FOUND)
        );
        badge.softDelete(securityUtils.getCurrentUsername());
    }
}
