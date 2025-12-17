package org.bf.pointservice.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.CustomResponse;
import org.bf.global.infrastructure.success.GeneralSuccessCode;
import org.bf.pointservice.application.command.PointCommandService;
import org.bf.pointservice.application.dto.PointGainRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointCommandService pointCommandService;

    @PostMapping
    public CustomResponse<?> getPoint(@Valid @RequestBody PointGainRequest request) {
        pointCommandService.gainPoint(request);
        return CustomResponse.onSuccess(GeneralSuccessCode.OK);
    }
}
