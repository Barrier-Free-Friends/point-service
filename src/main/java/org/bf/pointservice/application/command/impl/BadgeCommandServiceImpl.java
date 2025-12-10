package org.bf.pointservice.application.command.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bf.pointservice.application.command.BadgeCommandService;
import org.bf.pointservice.application.dto.BadgeCreateRequest;
import org.bf.pointservice.application.dto.BadgeResponse;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.bf.pointservice.domain.service.CheckPointGap;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeCommandServiceImpl implements BadgeCommandService {

    private final BadgeRepository badgeRepository;
    private final CheckPointGap checkPointGap;

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
}
