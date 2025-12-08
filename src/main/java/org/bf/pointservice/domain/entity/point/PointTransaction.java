package org.bf.pointservice.domain.entity.point;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.domain.Auditable;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.exception.point.PointTransactionErrorCode;

import java.util.UUID;

/**
 * 포인트 거래 내역
 * - 포인트 거래 타입을 포인트 획득, 사용, 거래 취소로 분리
 * - 포인트 취소 시 cancelled를 true로 변경하고 soft delete 처리하지 않음 (soft delete는 시스템적 삭제로만 처리)
 * - 포인트 취소 시 해당 거래 내역에 대한 취소 내역을 별도로 생성하고, 기존 거래 내역은 변경하지 않음
 * - 포인트 취소 시 취소된 항목을 또 다시 취소하지 않도록 cancelled로 확인
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

    @Column(nullable = false)
    private UUID sourceId;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int afterBalance;

    private UUID originalTransactionId;

    private boolean cancelled;

    @Builder(access = AccessLevel.PRIVATE)
    private PointTransaction(String sourceTable, UUID sourceId, Type type, int amount, int afterBalance, UUID originalTransactionId, boolean cancelled) {
        this.sourceTable = sourceTable;
        this.sourceId = sourceId;
        this.type = type;
        this.afterBalance = afterBalance;
        setAmount(amount);
        this.originalTransactionId = originalTransactionId;
        this.cancelled = cancelled;
    }

    public static PointTransaction createOriginal(String sourceTable, UUID sourceId, Type type, int amount, int afterBalance) {
        if (type == Type.CANCEL_GAIN || type == Type.CANCEL_USE) {
            throw new CustomException(PointTransactionErrorCode.INVALID_TYPE_OF_ORIGINAL);
        }
        return PointTransaction.builder()
                .sourceTable(sourceTable)
                .sourceId(sourceId)
                .type(type)
                .amount(amount)
                .afterBalance(afterBalance)
                .cancelled(false)
                .build();
    }

    public static PointTransaction createCancel(String sourceTable, UUID sourceId, Type type, int amount, int afterBalance, UUID originalTransactionId) {
        if (type == Type.GAIN || type == Type.USE) {
            throw new CustomException(PointTransactionErrorCode.INVALID_TYPE_OF_CANCEL);
        }
        return PointTransaction.builder()
                .sourceTable(sourceTable)
                .sourceId(sourceId)
                .type(type)
                .amount(amount)
                .afterBalance(afterBalance)
                .originalTransactionId(originalTransactionId)
                .cancelled(true)
                .build();
    }

    private void setAmount(int amount) {
        if (amount < 0) {
            throw new CustomException(PointTransactionErrorCode.INVALID_AMOUNT);
        }
        this.amount = amount;
    }
}
