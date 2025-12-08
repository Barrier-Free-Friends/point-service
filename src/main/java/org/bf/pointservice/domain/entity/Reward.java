package org.bf.pointservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.domain.Auditable;

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

    private String rewardName;
    private int price;
    private String descriptions;
}
