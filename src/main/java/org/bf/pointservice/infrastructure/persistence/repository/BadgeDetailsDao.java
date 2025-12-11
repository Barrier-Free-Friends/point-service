package org.bf.pointservice.infrastructure.persistence.repository;

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

    /**
     * 누적 포인트(totalPoint)에 해당하는 가장 높은 등급의 뱃지를 탐색
     */
    @Override
    public Optional<Badge> findByPointRange(long totalPoint) {
        QBadge qBadge = QBadge.badge;

        Badge foundBadge = queryFactory
                .selectFrom(qBadge)
                .where(
                        qBadge.minPoint.loe(totalPoint),
                        qBadge.deletedAt.isNull()
                )
                .orderBy(qBadge.minPoint.desc())
                .fetchFirst();

        return Optional.ofNullable(foundBadge);
    }
}
