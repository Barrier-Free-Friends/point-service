package org.bf.pointservice.domain.repository.reward;

import jakarta.persistence.LockModeType;
import org.bf.pointservice.domain.entity.reward.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RewardRepository extends JpaRepository<Reward, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Reward> findByRewardIdAndDeletedAtIsNull(UUID rewardId);
    @Query("SELECT r FROM Reward r " +
            "WHERE r.deletedAt IS NULL " +
            "AND (r.expiredAt IS NULL OR r.expiredAt > :now) " +
            "AND (r.stock IS NULL OR r.stock > 0)")
    Page<Reward> findAllAvailable(LocalDateTime now, Pageable pageable);
}
