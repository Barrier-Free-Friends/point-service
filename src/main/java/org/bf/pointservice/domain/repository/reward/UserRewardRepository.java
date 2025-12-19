package org.bf.pointservice.domain.repository.reward;

import org.bf.pointservice.domain.entity.reward.Status;
import org.bf.pointservice.domain.entity.reward.UserReward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserRewardRepository extends JpaRepository<UserReward, UUID> {
    Optional<UserReward> findByUserIdAndRewardId(UUID userId, UUID rewardId);
    @Query("SELECT ur FROM UserReward ur " +
            "WHERE ur.userId = :userId " +
            "AND ur.status = :status " +
            "AND (ur.rewardSnapshot.expiredAt IS NULL OR ur.rewardSnapshot.expiredAt > :now)")
    Page<UserReward> findAvailableRewards(@Param("userId") UUID userId,
                                          @Param("status") Status status,
                                          @Param("now") LocalDateTime now,
                                          Pageable pageable);
}
