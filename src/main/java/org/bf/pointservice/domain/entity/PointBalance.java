package org.bf.pointservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.domain.Auditable;

import java.util.UUID;

/**
 * 포인트 현재 잔액
 * - 사용자가 현재 보유한 포인트 잔액 기록
 * - 사용자가 현재까지 획득한 누적 포인트 기록
 * - 누적 포인트에 따라 뱃지 업그레이드
 * */
@Table(name = "p_point_balance")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointBalance extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private int currentBalance;
    private long totalAccumulatedBalance;
    private UUID currentBadgeId;
}
