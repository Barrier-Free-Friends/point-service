package org.bf.pointservice.domain.entity.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.domain.Auditable;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.exception.point.PointBalanceErrorCode;

import java.util.UUID;

/**
 * 포인트 현재 잔액
 * - 사용자가 현재 보유한 포인트 잔액 기록
 * - 사용자가 현재까지 획득한 누적 포인트 기록
 * - 누적 포인트에 따라 뱃지 업그레이드
 * - 포인트 현재 잔액 테이블을 사용자가 회원 가입할 경우 현재 포인트 0, 누적 포인트 0으로 자동 생성
 * - 현재 포인트 잔액 및 누적 포인트는 음수가 될 수 없음
 * */
@Table(name = "p_point_balance")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointBalance extends Auditable {
    @Id
    private UUID userId;

    @Column(nullable = false)
    private int currentBalance;

    @Column(nullable = false)
    private long totalAccumulatedBalance;

    private UUID currentBadgeId;

    @Builder
    public PointBalance(UUID userId) {
        this.userId = userId;
        this.currentBalance = 0;
        this.totalAccumulatedBalance = 0;
    }

    /**
     * 포인트 획득 시 현재 잔액, 누적 포인트 업데이트
     * */
    public void gainPoints(int points) {
        if (points < 0) {
            throw new CustomException(PointBalanceErrorCode.INVALID_GAIN_POINT);
        }
        this.currentBalance += points;
        this.totalAccumulatedBalance += points;
    }

    /**
     * 포인트 사용 시 현재 잔액 업데이트
     * */
    public void usePoints(int points) {
        if (points < 0) {
            throw new CustomException(PointBalanceErrorCode.INVALID_USE_POINT);
        }
        if (this.currentBalance -  points < 0) {
            throw new CustomException(PointBalanceErrorCode.INVALID_POINT_BALANCE);
        }
        this.currentBalance -= points;
    }

    /**
     * 포인트 획득 취소
     * */
    public void cancelGain(int points) {
        if (points < 0) {
            throw new CustomException(PointBalanceErrorCode.INVALID_GAIN_POINT);
        }
        if (this.currentBalance -  points < 0) {
            throw new CustomException(PointBalanceErrorCode.INVALID_POINT_BALANCE);
        }
        this.currentBalance -= points;
        if (this.totalAccumulatedBalance - points < 0) {
            throw new CustomException(PointBalanceErrorCode.INVALID_TOTAL_POINT);
        }
        this.totalAccumulatedBalance -= points;
    }

    /**
     * 포인트 사용 취소
     * */
    public void cancelUse(int points) {
        if (points < 0) {
            throw new CustomException(PointBalanceErrorCode.INVALID_USE_POINT);
        }
        this.currentBalance += points;
    }

    /**
     * 현재 보유한 뱃지 업데이트
     * */
    public void updateBadge(UUID badgeId) {
        if (badgeId != null && !this.currentBadgeId.equals(badgeId)) {
            this.currentBadgeId = badgeId;
        }
    }
}
