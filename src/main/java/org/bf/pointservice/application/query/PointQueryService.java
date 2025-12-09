package org.bf.pointservice.application.query;

import org.bf.pointservice.application.dto.PointBalanceResponse;
import org.bf.pointservice.application.dto.PointTransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointQueryService {
    // 현재 포인트 잔액 조회
    PointBalanceResponse getCurrentBalance();

    // 포인트 거래(사용 및 획득) 내역 조회
    Page<PointTransactionResponse> getTransactions(Pageable pageable);
}
