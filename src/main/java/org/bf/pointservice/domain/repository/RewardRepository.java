package org.bf.pointservice.domain.repository;

import org.bf.pointservice.domain.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RewardRepository extends JpaRepository<Reward, UUID> {
}
