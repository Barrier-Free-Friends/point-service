package org.bf.pointservice.domain.repository;

import org.bf.pointservice.domain.entity.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRewardRepository extends JpaRepository<UserReward, UUID> {
}
