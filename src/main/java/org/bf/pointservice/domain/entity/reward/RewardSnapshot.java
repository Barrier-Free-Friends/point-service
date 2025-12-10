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
    private String description;

    RewardSnapshot(String rewardName, int acquiredPrice, String description) {
        this.rewardName = rewardName;
        this.acquiredPrice = acquiredPrice;
        this.description = description;
    }
}
