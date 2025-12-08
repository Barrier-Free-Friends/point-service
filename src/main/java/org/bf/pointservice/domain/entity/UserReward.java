package org.bf.pointservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 사용자가 획득한 보상 목록 (관계 테이블)
 * - 보상을 획득한 시점의 보상 정보를 snapshot으로 기록
 * */
@Table(name = "p_user_reward")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReward {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;
    private UUID rewardId;
    private LocalDateTime acquireAt;
    private Status status;

    @Embedded
    private RewardSnapshot rewardSnapshot;
}
