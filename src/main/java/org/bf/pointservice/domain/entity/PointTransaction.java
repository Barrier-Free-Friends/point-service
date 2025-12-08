package org.bf.pointservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.domain.Auditable;

import java.util.UUID;

/**
 * 포인트 거래 내역
 * - 포인트 거래 타입을 포인트 획득, 사용, 거래 취소로 분리
 * - 포인트 취소 시 거래 타입을 변경하고 soft delete 처리하지 않음 (soft delete는 시스템적 삭제로만 처리)
 * - amount는 양수로만 기입 (+/-는 거래 타입으로 구분)
 * */
@Table(name = "p_point_transaction")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointTransaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    private String sourceTable;
    private UUID sourceId;
    private Type type;
    private int amount;
    private int afterBalance;
}
