package org.bf.pointservice.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.CustomResponse;
import org.bf.global.infrastructure.success.GeneralSuccessCode;
import org.bf.pointservice.application.command.PointCommandService;
import org.bf.pointservice.application.dto.PointGainRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "포인트 획득 (테스트) API")
@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointCommandService pointCommandService;

    @Operation(summary = "포인트 획득 테스트")
    @PostMapping
    public CustomResponse<?> getPoint(@Valid @RequestBody PointGainRequest request) {
        pointCommandService.gainPoint(request);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK);
    }
}
