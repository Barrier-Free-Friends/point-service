package org.bf.pointservice.application.command;

import org.bf.pointservice.application.dto.BadgeCreateRequest;
import org.bf.pointservice.application.dto.BadgeResponse;
import org.bf.pointservice.application.dto.BadgeUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 뱃지 생성, 수정, 삭제 서비스
 * */
public interface BadgeCommandService {
    /**
     * 신규 뱃지 생성
     * - 뱃지 아이콘 이미지 업로드
     * */
    BadgeResponse createBadge(BadgeCreateRequest request, MultipartFile file);

    /**
     * 뱃지 정보 수정
     * */
    BadgeResponse updateBadge(UUID badgeId, BadgeUpdateRequest request);

    /**
     * 뱃지 삭제
     * - soft delete 처리
     * */
    void deleteBadge(UUID badgeId);
}
