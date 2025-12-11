package org.bf.pointservice.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProcessedEventId implements Serializable {
    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "group_id", nullable = false)
    private String groupId;
}
