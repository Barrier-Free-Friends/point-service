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
     * 1. 초급 뱃지: [0, 99]
     * 2. 중급 뱃지: [200, 299]
     */
    @BeforeEach
    void setup() {
        badgeRepository.deleteAll();

        // 1. 초급 뱃지: [0, 99]
        Badge beginnerBadge = Badge.builder()
                .badgeName("초급 뱃지")
                .minPoint(0)
                .maxPoint(99)
                .checkPointGap(checkPointGap)
                .build();

        // 2. 중급 뱃지: [200, 299]
        Badge intermediateBadge = Badge.builder()
                .badgeName("중급 뱃지")
                .minPoint(200)
                .maxPoint(299)
                .checkPointGap(checkPointGap)
                .build();

        badgeRepository.save(beginnerBadge);
        badgeRepository.save(intermediateBadge);
    }

    @Test
    @DisplayName("기존 구간과 완전히 분리되어 겹치지 않는 경우")
    void testValidGap_NoOverlap() {
        // 새로운 뱃지 구간: [100, 199]
        // 기존 뱃지: [0, 99]와 [200, 299] 사이에 정확히 위치
        long newMin = 100;
        long newMax = 199;

        // 기대 결과: 겹치지 않으므로 true
        assertTrue(checkPointGap.validPointGap(newMin, newMax), "겹치는 구간이 없어야 합니다.");
    }

    @Test
    @DisplayName("새 구간이 기존 초급 뱃지의 내부와 겹치는 경우")
    void testInvalidGap_OverlapInternal() {
        // 새로운 뱃지 구간: [50, 150]
        // 초급 뱃지 [0, 99]와 [50, 99] 부분이 겹침
        long newMin = 50;
        long newMax = 150;

        // 기대 결과: 겹치므로 false
        assertFalse(checkPointGap.validPointGap(newMin, newMax), "기존 뱃지의 시작점과 겹쳐야 합니다.");
    }

    @Test
    @DisplayName("새 구간이 기존 중급 뱃지를 완전히 감싸는 경우")
    void testInvalidGap_OverlapWrapping() {
        // 새로운 뱃지 구간: [150, 350]
        // 중급 뱃지 [200, 299]를 완전히 감쌈
        long newMin = 150;
        long newMax = 350;

        // 기대 결과: 겹치므로 false
        assertFalse(checkPointGap.validPointGap(newMin, newMax), "기존 뱃지를 완전히 감싸면 안 됩니다.");
    }

    @Test
    @DisplayName("새 구간이 기존 초급 뱃지의 끝점과 겹치는 경우")
    void testInvalidGap_OverlapEndBoundary() {
        // 새로운 뱃지 구간: [99, 150]
        // 초급 뱃지 [0, 99]의 끝점 99와 겹침
        long newMin = 99;
        long newMax = 150;

        // 기대 결과: 겹치므로 false (99에서 겹침)
        assertFalse(checkPointGap.validPointGap(newMin, newMax), "기존 뱃지의 끝점과 겹치면 안 됩니다.");
    }

    @Test
    @DisplayName("경계값(Boundary)에서 겹치지 않고 정확히 이어지는 경우")
    void testValidGap_ExactBoundary() {
        // 새로운 뱃지 구간: [300, 399]
        // 중급 뱃지 [200, 299]의 바로 다음 구간
        long newMin = 300;
        long newMax = 399;

        // 기대 결과: 겹치지 않으므로 true
        assertTrue(checkPointGap.validPointGap(newMin, newMax), "경계에서 정확히 이어지는 것은 겹침이 아닙니다.");
    }
}
