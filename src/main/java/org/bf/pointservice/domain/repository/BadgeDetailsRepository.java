package org.bf.pointservice.domain.repository;

public interface BadgeDetailsRepository {
    boolean existsByPointRangeOverLap(long minPoint, long maxPoint);
}
