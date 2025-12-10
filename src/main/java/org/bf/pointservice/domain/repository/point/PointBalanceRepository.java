package org.bf.pointservice.domain.repository.point;

import jakarta.persistence.LockModeType;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface PointBalanceRepository extends JpaRepository<PointBalance, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PointBalance> findByUserIdAndDeletedAtIsNull(UUID userId);
}
