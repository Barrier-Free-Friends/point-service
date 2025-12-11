package org.bf.pointservice.infrastructure.persistence.repository;

import org.bf.pointservice.infrastructure.persistence.entity.ProcessedEvent;
import org.bf.pointservice.infrastructure.persistence.entity.ProcessedEventId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, ProcessedEventId> {
    boolean existsById_EventIdAndId_GroupId(String eventId, String groupId);
}
