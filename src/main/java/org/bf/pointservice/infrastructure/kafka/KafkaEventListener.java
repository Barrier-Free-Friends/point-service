package org.bf.pointservice.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bf.pointservice.application.command.PointCommandService;
import org.bf.pointservice.application.dto.PointGainRequest;
import org.bf.pointservice.domain.event.ReportCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventListener {

    private final PointCommandService pointCommandService;

    @KafkaListener(
            topics = "report-events",
            groupId = "point-service-group",
            containerFactory = "genericKafkaListenerContainerFactory"
    )
    public void handlePointGain(ReportCreatedEvent event) {
        log.info("[ReportCreatedEvent] 수신 완료. 이벤트: {}", event);
        pointCommandService.gainPoint(PointGainRequest.from(event));
    }
}
