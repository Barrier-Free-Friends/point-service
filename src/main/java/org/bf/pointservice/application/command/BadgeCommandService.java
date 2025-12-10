package org.bf.pointservice.application.command;

import org.bf.pointservice.application.dto.BadgeCreateRequest;
import org.bf.pointservice.application.dto.BadgeResponse;

public interface BadgeCommandService {
    // 신규 뱃지 생성
    BadgeResponse createBadge(BadgeCreateRequest request);
}
