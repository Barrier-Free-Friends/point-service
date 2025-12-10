package org.bf.pointservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.repository.badge.BadgeRepository;
import org.bf.pointservice.domain.service.CheckPointGap;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckPointGapImpl implements CheckPointGap {

    private final BadgeRepository badgeRepository;

    @Override
    public boolean validPointGap(long minPoint) {
        return !badgeRepository.existsByMinPointAndDeletedAtIsNull(minPoint);
    }
}
