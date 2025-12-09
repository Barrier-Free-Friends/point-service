package org.bf.pointservice.domain.service;

import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class CheckPointGapTest {
    @Autowired
    private CheckPointGap checkPointGap; // 테스트 대상 서비스

    @Autowired
    private BadgeRepository badgeRepository; // 데이터 설정을 위한 Repository (JpaRepository 상속 가정)

    /**
     * 테스트 실행 전, 기준이 되는 겹치지 않는 뱃지 2개를 먼저 DB에 저장
     * 1. 초급 뱃지: [0, ~]
     * 2. 중급 뱃지: [200, ~]
     */
    @BeforeEach
    void setup() {
        badgeRepository.deleteAll();

        // 1. 초급 뱃지: [0, ~]
        Badge beginnerBadge = Badge.builder()
                .badgeName("초급 뱃지")
                .minPoint(0)
                .checkPointGap(checkPointGap)
                .build();

        // 2. 중급 뱃지: [200, ~]
        Badge intermediateBadge = Badge.builder()
                .badgeName("중급 뱃지")
                .minPoint(200)
                .checkPointGap(checkPointGap)
                .build();

        badgeRepository.save(beginnerBadge);
        badgeRepository.save(intermediateBadge);
    }

    @Test
    @DisplayName("기존 구간과 완전히 분리되어 겹치지 않는 경우")
    void testValidGap_NoOverlap() {
        long newMin = 100;
        // 기대 결과: 겹치지 않으므로 true
        assertTrue(checkPointGap.validPointGap(newMin), "겹치는 구간이 없어야 합니다.");
    }

    @Test
    @DisplayName("새 구간이 기존 초급 뱃지의 내부와 겹치는 경우")
    void testInvalidGap_OverlapInternal() {
        long newMin = 200;
        // 기대 결과: 겹치므로 false
        assertFalse(checkPointGap.validPointGap(newMin), "기존 뱃지의 시작점과 겹쳐야 합니다.");
    }
}
