package org.bf.pointservice.domain.repository.point;

import jakarta.persistence.LockModeType;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PointBalanceRepository extends JpaRepository<PointBalance, UUID> {
    // 일반적인 조회
    Optional<PointBalance> findByUserIdAndDeletedAtIsNull(UUID userId);

    // 포인트 적립/차감 등 '쓰기' 작업이 빈번할 때만 사용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT pb FROM PointBalance pb WHERE pb.userId = :userId AND pb.deletedAt IS NULL")
    Optional<PointBalance> findByUserIdForUpdate(@Param("userId") UUID userId);
}
