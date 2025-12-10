package org.bf.pointservice.domain.repository.badge;

import org.bf.pointservice.domain.entity.badge.Badge;

import java.util.Optional;

public interface BadgeDetailsRepository {
    Optional<Badge> findByPointRange(long totalPoint);
}
