package org.bf.pointservice.application.query.impl;

import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.global.security.SecurityUtils;
import org.bf.pointservice.application.dto.PointBalanceResponse;
import org.bf.pointservice.application.dto.PointTransactionResponse;
import org.bf.pointservice.application.query.PointQueryService;
import org.bf.pointservice.domain.entity.badge.Badge;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.entity.point.PointTransaction;
import org.bf.pointservice.domain.exception.badge.BadgeErrorCode;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.bf.pointservice.domain.repository.point.PointBalanceRepository;
import org.bf.pointservice.domain.repository.point.PointTransactionRepository;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PointQueryServiceImpl implements PointQueryService {

    private final SecurityUtils securityUtils;
    private final PointBalanceRepository pointBalanceRepository;
    private final BadgeRepository badgeRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final BadgeUpdateService badgeUpdateService;

    /**
     * 로그인한 유저의 현재 포인트 잔액 조회
     * */
    @Override
    public PointBalanceResponse getCurrentBalance() {
        UUID currentUserId = securityUtils.getCurrentUserId();

        Optional<PointBalance> optionalBalance = pointBalanceRepository.findByUserIdAndDeletedAtIsNull(currentUserId);

        if (optionalBalance.isEmpty()) {
            // 엔티티가 없는 경우, DTO를 '잔액 0' 상태로 직접 구성하여 반환
            return PointBalanceResponse.fromZeroPointUser(currentUserId);
        }

        PointBalance pointBalance = optionalBalance.get();

        // 누적 포인트에 따른 할당 뱃지 lazy-update
        badgeUpdateService.updateBadge(pointBalance);

        // 업데이트한 뱃지 정보 조회
        Badge badge = badgeRepository.findByBadgeIdAndDeletedAtIsNull(pointBalance.getCurrentBadgeId())
                .orElseThrow(() -> new CustomException(BadgeErrorCode.BADGE_NOT_FOUND));

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
