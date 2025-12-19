package org.bf.pointservice.domain.repository.point;

import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.entity.point.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, UUID> {
    Optional<PointTransaction> findByTransactionIdAndDeletedAtIsNull(UUID transactionId);
    Page<PointTransaction> findByUserIdAndDeletedAtIsNull(UUID userId, Pageable pageable);
    Optional<PointTransaction> findByUserIdAndSourceIdAndDeletedAtIsNull(UUID userId, UUID sourceId);
    Optional<PointTransaction> findByUserIdAndSourceTableAndSourceIdAndTypeAndDeletedAtIsNull(UUID userId, String sourceTable, UUID sourceId, Type type);
}
