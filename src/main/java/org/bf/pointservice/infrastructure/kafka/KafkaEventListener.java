package org.bf.pointservice.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bf.pointservice.application.event.ReportEventHandler;
import org.bf.global.infrastructure.event.ReportCreatedEvent;
import org.bf.global.infrastructure.event.ReportDeletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventListener {

    private final ReportEventHandler reportEventHandler;

    @KafkaListener(
            topics = "report-created-events",
            groupId = "point-service-group",
            containerFactory = "genericKafkaListenerContainerFactory"
    )
    public void handleEvent(ReportCreatedEvent event) {
        log.info("[ReportCreatedEvent] 수신 완료. 이벤트: {}", event);
        reportEventHandler.handleReportCreatedEvent(event, "point-service-group");
    }

    @KafkaListener(
            topics = "report-deleted-events",
            groupId = "point-service-group",
            containerFactory = "genericKafkaListenerContainerFactory"
    )
    public void handlePointCancel(ReportDeletedEvent event) {
        log.info("[ReportDeletedEvent] 수신 완료. 이벤트: {}", event);
        reportEventHandler.handleReportDeletedEvent(event, "point-service-group");
    }
}
