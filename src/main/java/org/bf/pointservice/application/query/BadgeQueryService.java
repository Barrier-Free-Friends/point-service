package org.bf.pointservice.application.query;

import org.bf.pointservice.application.dto.BadgeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BadgeQueryService {
    // 뱃지 목록 조회
    Page<BadgeResponse> getBadges(Pageable pageable);

    // 단일 뱃지 조회
    BadgeResponse getBadge(UUID badgeId);
}
