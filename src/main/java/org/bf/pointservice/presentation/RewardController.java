package org.bf.pointservice.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.CustomResponse;
import org.bf.global.infrastructure.success.GeneralSuccessCode;
import org.bf.pointservice.application.command.RewardCommandService;
import org.bf.pointservice.application.dto.RewardCreateRequest;
import org.bf.pointservice.application.dto.RewardResponse;
import org.bf.pointservice.application.dto.RewardUpdateRequest;
import org.bf.pointservice.application.query.RewardQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardCommandService rewardCommandService;
    private final RewardQueryService rewardQueryService;

    /**
     * 신규 보상 생성
     * */
    @PostMapping
    public CustomResponse<RewardResponse> createReward(@Valid @RequestBody RewardCreateRequest request) {
        RewardResponse response = rewardCommandService.createReward(request);
        return CustomResponse.onSuccess(GeneralSuccessCode.CREATED, response);
    }

    /**
     * 보상 정보 수정
     * */
    @PatchMapping("/{rewardId}")
    public CustomResponse<RewardResponse> updateReward(@PathVariable("rewardId")UUID rewardId, @Valid @RequestBody RewardUpdateRequest request) {
        RewardResponse response = rewardCommandService.updateReward(rewardId, request);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    /**
     * 보상 삭제
     * */
    @DeleteMapping("/{rewardId}")
    public CustomResponse<?> deleteReward(@PathVariable("rewardId") UUID rewardId) {
        rewardCommandService.deleteReward(rewardId);
        return CustomResponse.onSuccess(GeneralSuccessCode.DELETED);
    }

    /**
     * 모든 보상 목록 조회
     * */
    @GetMapping
    public CustomResponse<Page<RewardResponse>> getRewards(Pageable pageable) {
        Page<RewardResponse> response = rewardQueryService.getRewards(pageable);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    /**
     * 단일 보상 정보 조회
     * */
    @GetMapping("/{rewardId}")
    public CustomResponse<RewardResponse> getReward(@PathVariable("rewardId") UUID rewardId) {
        RewardResponse response = rewardQueryService.getReward(rewardId);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK, response);
    }
}
