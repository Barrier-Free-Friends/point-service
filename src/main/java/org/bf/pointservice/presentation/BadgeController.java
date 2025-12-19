package org.bf.pointservice.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.CustomResponse;
import org.bf.global.infrastructure.success.GeneralSuccessCode;
import org.bf.pointservice.application.command.BadgeCommandService;
import org.bf.pointservice.application.dto.BadgeCreateRequest;
import org.bf.pointservice.application.dto.BadgeImageResponse;
import org.bf.pointservice.application.dto.BadgeResponse;
import org.bf.pointservice.application.dto.BadgeUpdateRequest;
import org.bf.pointservice.application.query.BadgeQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/badges")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeCommandService badgeCommandService;
    private final BadgeQueryService badgeQueryService;

    /**
     * 신규 뱃지 생성
     * */
    @PostMapping
    public CustomResponse<BadgeResponse> createBadge(@Valid @RequestPart("request") BadgeCreateRequest request, @RequestPart("file") MultipartFile file) {
        BadgeResponse response = badgeCommandService.createBadge(request, file);
        return CustomResponse.onSuccess(GeneralSuccessCode.CREATED, response);
    }

    /**
     * 뱃지 정보 수정
     * */
    @PatchMapping("{badgeId}")
    public CustomResponse<BadgeResponse> updateBadge(@PathVariable("badgeId") UUID badgeId, @Valid @RequestBody BadgeUpdateRequest request) {
        BadgeResponse response = badgeCommandService.updateBadge(badgeId, request);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    /**
     * 뱃지 삭제
     * */
    @DeleteMapping("{badgeId}")
    public CustomResponse<?> deleteBadge(@PathVariable("badgeId") UUID badgeId) {
        badgeCommandService.deleteBadge(badgeId);
        return CustomResponse.onSuccess(GeneralSuccessCode.DELETED);
    }

    /**
     * 뱃지 목록 조회
     * */
    @GetMapping
    public CustomResponse<Page<BadgeResponse>> getBadges(Pageable pageable) {
        Page<BadgeResponse> response = badgeQueryService.getBadges(pageable);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    /**
     * 단일 뱃지 정보 조회
     * */
    @GetMapping("{badgeId}")
    public CustomResponse<BadgeResponse> getBadge(@PathVariable("badgeId") UUID badgeId) {
        BadgeResponse response = badgeQueryService.getBadge(badgeId);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    /**
     * 프론트에서 뱃지 이미지를 조회
     * */
    @GetMapping("/image/{userId}")
    public CustomResponse<BadgeImageResponse> getBadgeImage(@PathVariable UUID userId) {
        String url = badgeQueryService.getBadgeImage(userId);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK, new BadgeImageResponse(url));
    }
}
