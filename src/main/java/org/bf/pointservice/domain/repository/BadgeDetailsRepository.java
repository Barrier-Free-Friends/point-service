package org.bf.pointservice.domain.repository;

import org.bf.pointservice.domain.entity.Badge;

import java.util.Optional;

public interface BadgeDetailsRepository {
    boolean existsByPointRangeOverLap(long minPoint, long maxPoint);
    Optional<Badge> findByPointRange(long totalPoint);
}
