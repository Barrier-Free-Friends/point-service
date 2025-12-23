package org.bf.pointservice.application.query.impl;

import lombok.RequiredArgsConstructor;
import org.bf.global.security.SecurityUtils;
import org.bf.pointservice.application.dto.PointBalanceResponse;
import org.bf.pointservice.application.dto.PointTransactionResponse;
import org.bf.pointservice.application.query.PointQueryService;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.repository.point.PointBalanceRepository;
import org.bf.pointservice.domain.repository.point.PointTransactionRepository;
import org.bf.pointservice.domain.service.BadgePolicyService;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PointQueryServiceImpl implements PointQueryService {

    private final SecurityUtils securityUtils;
    private final PointBalanceRepository pointBalanceRepository;
    private final BadgePolicyService badgePolicyService;
    private final PointTransactionRepository pointTransactionRepository;
    private final BadgeUpdateService badgeUpdateService;

    /**
     * 로그인한 유저의 현재 포인트 잔액 조회
     * */
    @Override
    public PointBalanceResponse getCurrentBalance() {
        UUID currentUserId = securityUtils.getCurrentUserId();

        PointBalance pointBalance = pointBalanceRepository.findByUserIdAndDeletedAtIsNull(currentUserId)
                .orElseGet(() -> null);

        if (pointBalance == null) {
            return PointBalanceResponse.fromZeroPointUser(currentUserId);
        }

        // 시스템 정책 뱃지 버전 확인
        Long latestVersion = badgePolicyService.getLastestVersion();

        // 버전 비교: 유저의 버전이 최신과 다를 때만 뱃지 판정 및 업데이트 로직 실행
        if (!Objects.equals(pointBalance.getBadgeVersion(), latestVersion)) {
            badgeUpdateService.updateBadge(pointBalance);
        }

        Badge badge = badgePolicyService.getBadgeFromCache(pointBalance.getCurrentBadgeId());

        return PointBalanceResponse.from(pointBalance, badge);
    }

    /**
     * 로그인한 유저의 포인트 거래 내역 조회
     * */
    @Override
    public Page<PointTransactionResponse> getTransactions(Pageable pageable) {
        Page<PointTransaction> pointTransactions = pointTransactionRepository.findByUserIdAndDeletedAtIsNull(securityUtils.getCurrentUserId(), pageable);
        return pointTransactions.map(PointTransactionResponse::from);
    }
}
