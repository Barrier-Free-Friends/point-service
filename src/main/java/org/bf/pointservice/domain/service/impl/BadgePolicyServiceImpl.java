package org.bf.pointservice.domain.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.exception.badge.BadgeErrorCode;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.bf.pointservice.domain.service.BadgePolicyService;
import org.bf.pointservice.infrastructure.persistence.entity.PolicyMeta;
import org.bf.pointservice.infrastructure.persistence.repository.PolicyMetaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BadgePolicyServiceImpl implements BadgePolicyService {

    private final PolicyMetaRepository policyMetaRepository;
    private final BadgeRepository badgeRepository;
    private static final String POLICY_ID = "Badge Policy";

    @Transactional
    @CacheEvict(value = {"badgeVersion", "badges"}, allEntries = true)
    @Override
    public void updateVersion() {
        PolicyMeta meta = policyMetaRepository.findById(POLICY_ID)
                .orElseGet(() -> PolicyMeta.builder()
                        .id(POLICY_ID)
                        .badgeVersion(0L)
                        .build());

        meta.updateVersion(meta.getBadgeVersion() + 1);
        policyMetaRepository.save(meta);
    }

    /**
     * 개별 뱃지 상세 정보 조회
     * - DB 조인 없이 메모리에서 뱃지 정보를 즉시 가져옴
     */
    @Cacheable(value = "badges", key = "#badgeId")
    @Override
    public Badge getBadgeFromCache(UUID badgeId) {
        return badgeRepository.findByBadgeIdAndDeletedAtIsNull(badgeId)
                .orElseThrow(() -> new CustomException(BadgeErrorCode.BADGE_NOT_FOUND));
    }

    @Cacheable(value = "badgeVersion", key = "'latest'")
    @Transactional
    @Override
    public Long getLastestVersion() {
        return policyMetaRepository.findById(POLICY_ID)
                .map(PolicyMeta::getBadgeVersion)
                .orElseGet(() -> {
                    // 데이터가 없으면 새로 생성해서 저장
                    PolicyMeta newMeta = PolicyMeta.builder()
                            .id(POLICY_ID)
                            .badgeVersion(0L)
                            .build();
                    return policyMetaRepository.save(newMeta).getBadgeVersion();
                });
    }
}
