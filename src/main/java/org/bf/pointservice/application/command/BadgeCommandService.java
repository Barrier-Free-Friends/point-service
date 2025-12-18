package org.bf.pointservice.application.command;

import org.bf.pointservice.application.dto.BadgeCreateRequest;
import org.bf.pointservice.application.dto.BadgeResponse;
import org.bf.pointservice.application.dto.BadgeUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface BadgeCommandService {
    // 신규 뱃지 생성
    BadgeResponse createBadge(BadgeCreateRequest request, MultipartFile file);

    // 뱃지 정보 수정
    BadgeResponse updateBadge(UUID badgeId, BadgeUpdateRequest request);

    // 뱃지 삭제
    void deleteBadge(UUID badgeId);
}
