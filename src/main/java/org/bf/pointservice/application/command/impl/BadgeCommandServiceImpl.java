package org.bf.pointservice.application.command.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.global.security.SecurityUtils;
import org.bf.pointservice.application.command.BadgeCommandService;
import org.bf.pointservice.application.dto.BadgeCreateRequest;
import org.bf.pointservice.application.dto.BadgeResponse;
import org.bf.pointservice.application.dto.BadgeUpdateRequest;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.exception.badge.BadgeErrorCode;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.bf.pointservice.domain.service.BadgePolicyService;
import org.bf.pointservice.domain.service.CheckPointGap;
import org.bf.pointservice.domain.service.ImageUploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeCommandServiceImpl implements BadgeCommandService {

    private final BadgeRepository badgeRepository;
    private final CheckPointGap checkPointGap;
    private final SecurityUtils securityUtils;
    private final ImageUploader imageUploader;
    private final BadgePolicyService badgePolicyService;

    /**
     * 신규 뱃지 생성
     * */
    @Override
    public BadgeResponse createBadge(BadgeCreateRequest request, MultipartFile file) {
        // 뱃지 이름 중복 확인
        if (badgeRepository.existsByBadgeNameAndDeletedAtIsNull(request.badgeName())) {
            throw new CustomException(BadgeErrorCode.INVALID_BADGE_NAME);
        }

        // 뱃지 아이콘 이미지 s3에 업로드
        String url = imageUploader.upload(file);

        // 신규 뱃지 생성
        Badge badge = Badge.builder()
                .badgeName(request.badgeName())
                .minPoint(request.minPoint())
                .descriptions(request.descriptions())
                .imgUrl(url)
                .checkPointGap(checkPointGap)
                .build();
        badgeRepository.save(badge);
        badgePolicyService.updateVersion();
        return BadgeResponse.from(badge);
    }

    /**
     * 뱃지 정보 수정
     * */
    @Override
    public BadgeResponse updateBadge(UUID badgeId, BadgeUpdateRequest request) {
        Badge badge = badgeRepository.findByBadgeIdAndDeletedAtIsNull(badgeId).orElseThrow(
                () -> new CustomException(BadgeErrorCode.BADGE_NOT_FOUND)
        );
        if (request.badgeName() != null && !request.badgeName().equals(badge.getBadgeName()) && !request.badgeName().isBlank()) {
            if (badgeRepository.existsByBadgeNameAndDeletedAtIsNull(request.badgeName())) {
                throw new CustomException(BadgeErrorCode.INVALID_BADGE_NAME);
            }
            badge.updateBadgeName(request.badgeName());
        }
        if (request.descriptions() != null && !request.descriptions().isEmpty()) {
            badge.updateDescriptions(request.descriptions());
        }
        if (request.minPoint() != null && !request.minPoint().equals(badge.getMinPoint())) {
            badge.updateMinPoint(request.minPoint(), checkPointGap);
        }
        badgePolicyService.updateVersion();
        return BadgeResponse.from(badge);
    }

    /**
     * 뱃지 삭제
     * - soft delete 처리
     * */
    @Override
    public void deleteBadge(UUID badgeId) {
        Badge badge = badgeRepository.findByBadgeIdAndDeletedAtIsNull(badgeId).orElseThrow(
                () -> new CustomException(BadgeErrorCode.BADGE_NOT_FOUND)
        );
        badge.softDelete(securityUtils.getCurrentUsername());
        badgePolicyService.updateVersion();
    }
}
