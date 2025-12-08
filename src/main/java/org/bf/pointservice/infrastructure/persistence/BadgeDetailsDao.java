package org.bf.pointservice.infrastructure.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.entity.QBadge;
import org.bf.pointservice.domain.repository.BadgeDetailsRepository;
import org.springframework.stereotype.Repository;

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
}
