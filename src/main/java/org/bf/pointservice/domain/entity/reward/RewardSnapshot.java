package org.bf.pointservice.domain.entity.reward;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardSnapshot {
    private String rewardName;
    private int acquiredPrice;
    private String descriptions;
    private LocalDateTime expiredAt;

    RewardSnapshot(String rewardName, int acquiredPrice, String descriptions, LocalDateTime expiredAt) {
        this.rewardName = rewardName;
        this.acquiredPrice = acquiredPrice;
        this.descriptions = descriptions;
        this.expiredAt = expiredAt;
    }
}
