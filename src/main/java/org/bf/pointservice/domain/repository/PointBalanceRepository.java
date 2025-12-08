package org.bf.pointservice.domain.repository;

import org.bf.pointservice.domain.entity.PointBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PointBalanceRepository extends JpaRepository<PointBalance, UUID> {
}
