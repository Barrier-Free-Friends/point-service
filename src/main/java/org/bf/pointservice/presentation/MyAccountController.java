package org.bf.pointservice.presentation;

import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.CustomResponse;
import org.bf.global.infrastructure.success.GeneralSuccessCode;
import org.bf.pointservice.application.dto.PointBalanceResponse;
import org.bf.pointservice.application.dto.PointTransactionResponse;
import org.bf.pointservice.application.query.PointQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MyAccountController {

    private final PointQueryService pointQueryService;

    /**
     * 현재 포인트 잔액 조회
     * */
    @GetMapping("/balances")
    public CustomResponse<PointBalanceResponse> getBalances() {
        PointBalanceResponse response = pointQueryService.getCurrentBalance();
        return CustomResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    /**
     * 거래 내역 조회
     * */
    @GetMapping("/histories")
    public CustomResponse<Page<PointTransactionResponse>> getHistories(Pageable pageable) {
        Page<PointTransactionResponse> response = pointQueryService.getTransactions(pageable);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK, response);
    }
}
