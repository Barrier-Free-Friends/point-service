package org.bf.pointservice.application.dto;

import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.entity.point.Type;

import java.util.UUID;

public record PointTransactionResponse(
        UUID transactionId,
        UUID userId,
        String sourceTable,
        UUID sourceId,
        Type type,
        int amount,
        int afterBalance,
        UUID originalTransactionId,
        boolean cancelled
) {
    public static PointTransactionResponse from(PointTransaction pointTransaction) {
        return new PointTransactionResponse(
                pointTransaction.getTransactionId(),
                pointTransaction.getUserId(),
                pointTransaction.getSourceTable(),
                pointTransaction.getSourceId(),
                pointTransaction.getType(),
                pointTransaction.getAmount(),
                pointTransaction.getAfterBalance(),
                pointTransaction.getOriginalTransactionId(),
                pointTransaction.isCancelled()
        );
    }
}
