package org.bf.pointservice.domain.repository.point;

import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, UUID> {
}
