package org.bf.pointservice.infrastructure.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "p_processed_event")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProcessedEvent {
    @EmbeddedId
    private ProcessedEventId id;

    private LocalDateTime processedAt;

    public ProcessedEvent(String eventId, String groupId) {
        this.id = new ProcessedEventId(eventId, groupId);
        this.processedAt = LocalDateTime.now();
    }
}
