package org.bf.pointservice.domain.entity.reward;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardSnapshot {
    private String rewardName;
    private int acquiredPrice;
    private String descriptions;

    RewardSnapshot(String rewardName, int acquiredPrice, String descriptions) {
        this.rewardName = rewardName;
        this.acquiredPrice = acquiredPrice;
        this.descriptions = descriptions;
    }
}
