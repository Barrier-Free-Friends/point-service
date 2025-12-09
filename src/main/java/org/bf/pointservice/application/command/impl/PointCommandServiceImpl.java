package org.bf.pointservice.application.command.impl;

import lombok.RequiredArgsConstructor;
import org.bf.global.security.SecurityUtils;
import org.bf.pointservice.application.command.PointCommandService;
import org.bf.pointservice.application.dto.PointGainRequest;
import org.bf.pointservice.application.dto.PointUseRequest;
import org.bf.pointservice.domain.service.PointCancellationService;
import org.bf.pointservice.domain.service.PointGainService;
import org.bf.pointservice.domain.service.PointUseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointCommandServiceImpl implements PointCommandService {

    private final SecurityUtils securityUtils;
    private final PointGainService pointGainService;
    private final PointUseService pointUseService;
    private final PointCancellationService pointCancellationService;

    @Override
    public void gainPoint(PointGainRequest request) {
        pointGainService.gainPoints(
                securityUtils.getCurrentUserId(),
                request.points(),
                request.sourceTable(),
                request.sourceId()
        );
    }

    @Override
    public void usePoint(PointUseRequest request) {
        pointUseService.usePoints(
                securityUtils.getCurrentUserId(),
                request.points(),
                request.sourceTable(),
                request.sourceId()
        );
    }

    @Override
    public void cancel(UUID transactionId) {
        pointCancellationService.cancel(transactionId);
    }
}
