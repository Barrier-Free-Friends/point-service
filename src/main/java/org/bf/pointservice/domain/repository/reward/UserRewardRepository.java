package org.bf.pointservice.domain.repository.reward;

import org.bf.pointservice.domain.entity.reward.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRewardRepository extends JpaRepository<UserReward, UUID> {
    Optional<UserReward> findByUserIdAndRewardId(UUID userId, UUID rewardId);
}
