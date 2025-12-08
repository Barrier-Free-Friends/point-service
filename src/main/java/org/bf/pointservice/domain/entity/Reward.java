package org.bf.pointservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.domain.Auditable;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.exception.RewardErrorCode;

import java.util.UUID;

/**
 * 포인트 사용하여 획득 가능한 보상 정의
 * */
@Table(name = "p_reward")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reward extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID rewardId;

    @Column(nullable = false, length = 100)
    private String rewardName;

    @Column(nullable = false)
    private int price;

    private String descriptions;

    @Builder
    public Reward(String rewardName, int price, String descriptions) {
        this.rewardName = rewardName;
        setPrice(price);
        this.descriptions = descriptions;
    }

    private void setPrice(int price) {
        if (price < 0) {
            throw new CustomException(RewardErrorCode.INVALID_PRICE);
        }
        this.price = price;
    }

    void updateName(String rewardName) {
        if (rewardName == null || rewardName.trim().isEmpty()) {
            throw new CustomException(RewardErrorCode.INVALID_REWARD_NAME);
        }
        this.rewardName = rewardName;
    }

    void updatePrice(int price) {
        setPrice(price);
    }

    void updateDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
