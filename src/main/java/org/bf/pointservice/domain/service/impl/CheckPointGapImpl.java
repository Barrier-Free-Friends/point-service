package org.bf.pointservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.bf.pointservice.domain.service.CheckPointGap;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckPointGapImpl implements CheckPointGap {

    private final BadgeRepository badgeRepository;

    /**
     * 이미 해당 최소 포인트 요건을 가진 뱃지가 존재하는지 검증
     * */
    @Override
    public boolean validPointGap(long minPoint) {
        return !badgeRepository.existsByMinPointAndDeletedAtIsNull(minPoint);
    }
}
