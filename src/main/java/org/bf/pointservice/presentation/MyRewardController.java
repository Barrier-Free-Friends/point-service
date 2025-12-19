package org.bf.pointservice.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.CustomResponse;
import org.bf.global.infrastructure.success.GeneralSuccessCode;
import org.bf.global.security.SecurityUtils;
import org.bf.pointservice.application.dto.RewardResponse;
import org.bf.pointservice.application.query.RewardQueryService;
import org.bf.pointservice.domain.service.RewardPurchaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "사용자 보상 API")
@RestController
@RequestMapping("/me/rewards")
@RequiredArgsConstructor
public class MyRewardController {

    private final RewardPurchaseService rewardPurchaseService;
    private final SecurityUtils securityUtils;
    private final RewardQueryService rewardQueryService;

    /**
     * 포인트를 사용하여 보상 구매
     * */
    @Operation(summary = "포인트를 사용하여 보상 구매")
    @PostMapping("/{rewardId}")
    public CustomResponse<?> purchaseReward(@PathVariable("rewardId") UUID rewardId) {
        rewardPurchaseService.purchaseReward(securityUtils.getCurrentUserId(), rewardId);
        return CustomResponse.onSuccess(GeneralSuccessCode.CREATED);
    }

    /**
     * 사용자가 보유한 보상 목록 조회
     * */
    @Operation(summary = "보상 목록 조회")
    @GetMapping
    public CustomResponse<Page<RewardResponse>> getMyRewards(@PageableDefault(sort = "acquiredAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RewardResponse> response = rewardQueryService.getRewardsFromUser(pageable);
        return CustomResponse.onSuccess(response);
    }
}
