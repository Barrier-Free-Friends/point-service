package org.bf.pointservice.domain.repository.reward;

import org.bf.pointservice.domain.entity.reward.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RewardRepository extends JpaRepository<Reward, UUID> {
    Optional<Reward> findByRewardIdAndDeletedAtIsNull(UUID rewardId);
    Page<Reward> findAllByDeletedAtIsNull(Pageable pageable);
}
