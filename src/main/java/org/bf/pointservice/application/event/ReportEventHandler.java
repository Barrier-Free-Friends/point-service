package org.bf.pointservice.application.event;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bf.global.infrastructure.event.ReportCreatedEvent;
import org.bf.global.infrastructure.event.ReportDeletedEvent;
import org.bf.pointservice.application.command.PointCommandService;
import org.bf.pointservice.application.dto.PointCancelRequest;
import org.bf.pointservice.application.dto.PointGainRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReportEventHandler {

    private final PointCommandService pointCommandService;
    private final IdempotencyService idempotencyService;

    /**
     * 제보 생성 이벤트 처리
     * - 포인트 획득
     * */
    public void handleReportCreatedEvent(ReportCreatedEvent event, String groupId) {
        if (idempotencyService.isAlreadyProcessed(event.getEventId(), groupId)) {
            log.warn("이미 처리된 이벤트입니다. [{}]", event.getEventId());
            return;
        }
        try {
            log.info("이벤트 처리 시작");
            pointCommandService.gainPoint(PointGainRequest.from(event));

            idempotencyService.recordProcessedEvent(event.getEventId(), groupId);
        } catch (DataIntegrityViolationException ex) {
            if (idempotencyService.isUniqueConstraintViolation(ex)) {
                log.warn("이미 처리된 이벤트입니다. [{}]", event.getEventId());
                return;
            }
            throw ex;
        }
    }

    /**
     * 제보 삭제 이벤트 처리
     * - 포인트 획득 취소
     * */
    public void handleReportDeletedEvent(ReportDeletedEvent event, String groupId) {
        if (idempotencyService.isAlreadyProcessed(event.getEventId(), groupId)) {
            log.warn("이미 처리된 이벤트입니다. [{}]", event.getEventId());
            return;
        }
        try {
            pointCommandService.cancel(new PointCancelRequest(event.getUserId(), event.getSourceId()));

            idempotencyService.recordProcessedEvent(event.getEventId(), groupId);
        } catch (DataIntegrityViolationException ex) {
            if (idempotencyService.isUniqueConstraintViolation(ex)) {
                log.warn("이미 처리된 이벤트입니다. [{}]", event.getEventId());
                return;
            }
            throw ex;
        }
    }
}
