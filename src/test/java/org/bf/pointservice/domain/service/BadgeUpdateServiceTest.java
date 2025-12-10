package org.bf.pointservice.domain.service;

import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class BadgeUpdateServiceTest {
    @Autowired
    private BadgeUpdateService badgeUpdateService;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private CheckPointGap checkPointGap;

    private UUID BEGINNER_ID;
    private UUID MASTER_ID;


    /**
     * 테스트 실행 전, 기준이 되는 뱃지 2개를 DB에 저장
     * 1. 초급 뱃지 (BEGINNER_ID): [0, ~]
     * 2. 마스터 뱃지 (MASTER_ID): [500, ~]
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

        // 2. 마스터 뱃지: [500, ~]
        Badge masterBadge = Badge.builder()
                .badgeName("마스터 뱃지")
                .minPoint(500)
                .checkPointGap(checkPointGap)
                .build();

        badgeRepository.save(beginnerBadge);
        badgeRepository.save(masterBadge);

        BEGINNER_ID = beginnerBadge.getBadgeId();
        MASTER_ID = masterBadge.getBadgeId();
    }

    @Test
    @DisplayName("누적 포인트가 초급 뱃지 구간 내에 있을 때 ID를 반환해야 한다.")
    void testFindNewBadgeId_BeginnerRange() {
        long totalPoint = 50;

        UUID foundId = badgeUpdateService.findNewBadgeId(totalPoint);

        assertEquals(BEGINNER_ID, foundId, "50점은 초급 뱃지의 ID를 반환해야 합니다.");
    }

    @Test
    @DisplayName("누적 포인트가 마스터 뱃지 구간의 경계값일 때 ID를 반환해야 한다.")
    void testFindNewBadgeId_MasterBoundary() {
        long totalPoint = 999;

        UUID foundId = badgeUpdateService.findNewBadgeId(totalPoint);

        assertEquals(MASTER_ID, foundId, "999점은 마스터 뱃지의 ID를 반환해야 합니다.");
    }
}
