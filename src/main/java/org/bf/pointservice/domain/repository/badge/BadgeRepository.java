package org.bf.pointservice.domain.repository.badge;

import org.bf.pointservice.domain.entity.badge.Badge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BadgeRepository extends JpaRepository<Badge, UUID> {
    Optional<Badge> findByBadgeId(UUID badgeId);
    boolean existsByMinPoint(long minPoint);
    Page<Badge> findAllByDeletedAtIsNull(Pageable pageable);
}
