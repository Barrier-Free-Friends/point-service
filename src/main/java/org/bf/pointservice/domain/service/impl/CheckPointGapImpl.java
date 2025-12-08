package org.bf.pointservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.repository.badge.BadgeDetailsRepository;
import org.bf.pointservice.domain.service.CheckPointGap;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckPointGapImpl implements CheckPointGap {

    private final BadgeDetailsRepository badgeDetailsRepository;

    @Override
    public boolean validPointGap(long minPoint, long maxPoint) {
        return !badgeDetailsRepository.existsByPointRangeOverLap(minPoint, maxPoint);
    }
}
