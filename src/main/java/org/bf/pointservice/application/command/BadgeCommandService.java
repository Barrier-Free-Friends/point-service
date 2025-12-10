package org.bf.pointservice.application.command;

import org.bf.pointservice.application.dto.BadgeCreateRequest;
import org.bf.pointservice.application.dto.BadgeResponse;
import org.bf.pointservice.application.dto.BadgeUpdateRequest;

import java.util.UUID;

public interface BadgeCommandService {
    // 신규 뱃지 생성
    BadgeResponse createBadge(BadgeCreateRequest request);

    // 뱃지 정보 수정
    BadgeResponse updateBadge(BadgeUpdateRequest request);

    // 뱃지 삭제
    void deleteBadge(UUID badgeId);
}
