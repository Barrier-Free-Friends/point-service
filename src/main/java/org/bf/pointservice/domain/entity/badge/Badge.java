package org.bf.pointservice.domain.entity.badge;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.domain.Auditable;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.exception.badge.BadgeErrorCode;
import org.bf.pointservice.domain.service.CheckPointGap;

import java.util.UUID;

/**
 * 누적 포인트에 따른 뱃지 정의
 * - badge name은 필수, 겹치지 않게, 너무 길지 않도록 설정
 * - 뱃지 획득을 위한 최소 포인트 요건 필수
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
    private long minPoint;

    private String descriptions;
    private String imgUrl;

    @Builder
    public Badge(String badgeName, long minPoint, String descriptions, String imgUrl, CheckPointGap checkPointGap) {
        this.badgeName = badgeName;
        setMinPoint(minPoint, checkPointGap);
        this.descriptions = descriptions;
        this.imgUrl = imgUrl;
    }

    /**
     * 뱃지 이름 수정
     * */
    public void updateBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    /**
     * 뱃지 설명 수정
     * */
    public void updateDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    /**
     * 뱃지 사진 수정
     * */
    public void updateImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 뱃지 획득 조건 업데이트
     * */
    public void updateMinPoint(long minPoint, CheckPointGap checkPointGap) {
        setMinPoint(minPoint, checkPointGap);
    }

    /**
     * 뱃지 획득 조건 검증
     * */
    private void setMinPoint(long min, CheckPointGap checkPointGap) {
        if (!checkPointGap.validPointGap(min)) {
            throw new CustomException(BadgeErrorCode.INVALID_MIN_POINT);
        }
        if (min < 0) {
            throw new CustomException(BadgeErrorCode.INVALID_POINT_MINUS);
        }
        this.minPoint = min;
    }
}
