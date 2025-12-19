package org.bf.pointservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.entity.point.Type;

import java.util.UUID;

@Schema(description = "포인트 거래 내역 반환 DTO")
public record PointTransactionResponse(
        @Schema(description = "거래 내역 ID")
        UUID transactionId,

        @Schema(description = "유저 ID")
        UUID userId,

        @Schema(description = "포인트 획득 및 사용 원인 테이블")
        String sourceTable,

        @Schema(description = "포인트 획득 및 사용 원인 데이터 ID")
        UUID sourceId,

        @Schema(description = "거래 타입", example = "GAIN/USE/CANCEL_GAIN/CANCEL_USE")
        Type type,

        @Schema(description = "획득 및 사용 포인트 (양수로 표기)")
        int amount,

        @Schema(description = "거래 후 잔액 포인트")
        int afterBalance,

        @Schema(description = "거래 취소 시 원본 거래 내역 ID")
        UUID originalTransactionId,

        @Schema(description = "취소 여부")
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
