package org.bf.pointservice.domain.repository;

import org.bf.pointservice.domain.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BadgeRepository extends JpaRepository<Badge, UUID> {
}
