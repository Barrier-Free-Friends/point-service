package org.bf.pointservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.domain.Auditable;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.exception.BadgeErrorCode;
import org.bf.pointservice.domain.service.CheckPointGap;

import java.util.UUID;

/**
 * 누적 포인트에 따른 뱃지 정의
 * - badge name은 필수, 겹치지 않게, 너무 길지 않도록 설정
 * - 뱃지 획득을 위한 최소 포인트 요건과, 해당 뱃지를 유지하는 포인트 상한선 필수
 * - 신규 뱃지 생성 시 포인트 구간이 겹치지 않는지 확인 필수
 * */
@Table(name = "p_badge")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID badgeId;

    @Column(unique = true, nullable = false, length = 20)
    private String badgeName;

    @Column(nullable = false)
    private long minPoints;

    @Column(nullable = false)
    private long maxPoints;

    private String descriptions;
    private String imgUrl;

    @Builder
    public Badge(String badgeName, long minPoints, long maxPoints, String descriptions, String imgUrl, CheckPointGap checkPointGap) {
        this.badgeName = badgeName;
        setMinAndMaxPoints(minPoints, maxPoints, checkPointGap);
        this.descriptions = descriptions;
        this.imgUrl = imgUrl;
    }

    /**
     * 뱃지 유지를 위한 최소/최대 포인트 조건 검증
     * */
    private void setMinAndMaxPoints(long min, long max, CheckPointGap checkPointGap) {
        if (min < 0) {
            throw new CustomException(BadgeErrorCode.INVALID_POINT_MINUS);
        }
        if (min > max) {
            throw new CustomException(BadgeErrorCode.INVALID_MIN_POINT_OVER);
        }
        if (!checkPointGap.validPointGap(min, max)) {
            throw new CustomException(BadgeErrorCode.INVALID_POINT_GAP);
        }
        this.minPoints = min;
        this.maxPoints = max;
    }
}
