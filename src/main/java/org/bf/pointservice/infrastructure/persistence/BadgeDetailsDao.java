package org.bf.pointservice.infrastructure.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.entity.badge.QBadge;
import org.bf.pointservice.domain.repository.badge.BadgeDetailsRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BadgeDetailsDao implements BadgeDetailsRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByPointRangeOverLap(long minPoint, long maxPoint) {
        QBadge qBadge = QBadge.badge;

        // 겹치는 뱃지 구간이 존재하는지 확인
        Integer fetchOne = queryFactory
                .selectOne()
                .from(qBadge)
                .where(
                        qBadge.minPoint.loe(maxPoint).and(qBadge.maxPoint.goe(minPoint))
                )
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    public Optional<Badge> findByPointRange(long totalPoint) {
        QBadge qBadge = QBadge.badge;

        Badge foundBadge = queryFactory
                .selectFrom(qBadge)
                .where(
                        qBadge.minPoint.loe(totalPoint).and(qBadge.maxPoint.goe(totalPoint))
                )
                .fetchOne();

        return Optional.ofNullable(foundBadge);
    }
}
