package org.bf.pointservice.application.event;

import lombok.RequiredArgsConstructor;
import org.bf.pointservice.infrastructure.persistence.entity.ProcessedEvent;
import org.bf.pointservice.infrastructure.persistence.repository.ProcessedEventRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final ProcessedEventRepository processedEventRepository;

    /**
     * 이미 처리된 이벤트인지 확인 (처리된 이벤트일 경우 트랜잭션 조기 종료 유도)
     * */
    public boolean isAlreadyProcessed(String eventId, String groupId) {
        return processedEventRepository.existsById_EventIdAndId_GroupId(eventId, groupId);
    }

    /**
     * 최종 처리 기록 저장
     * - EventHandler의 @Transactional 내부 호출
     * - unique constraint를 통한 동시성 제어
     * */
    public void recordProcessedEvent(String eventId, String groupId) {
        processedEventRepository.save(new ProcessedEvent(eventId, groupId));
    }

    /**
     * DataIntegrityViolationException이 Unique Constraint Violation인지 확인
     * */
    public boolean isUniqueConstraintViolation(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof org.hibernate.exception.ConstraintViolationException) {
            if (rootCause.getCause() instanceof java.sql.SQLException sqlException) {
                String sqlState = sqlException.getSQLState();

                // PostgreSQL: SQL State '23505'는 unique_violation을 의미
                return "23505".equals(sqlState);
            }
        }
        return false;
    }
}
