package org.bf.pointservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.infrastructure.exception.CustomException;
import org.bf.pointservice.domain.exception.RewardErrorCode;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 사용자가 획득한 보상 목록 (관계 테이블)
 * - 보상을 획득한 시점의 보상 정보를 snapshot으로 기록
 * */
@Table(
        name = "p_user_reward",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "reward_id"})
)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReward {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID rewardId;

    @Column(nullable = false)
    private LocalDateTime acquireAt;

    @Column(nullable = false)
    private Status status;

    @Embedded
    private RewardSnapshot rewardSnapshot;

    @Builder
    public UserReward(UUID userId, UUID rewardId, String rewardName, int acquiredPrice, String description) {
        if (userId == null || rewardId == null) {
            throw new CustomException(RewardErrorCode.INVALID_RELATION);
        }
        if (rewardName == null || rewardName.isEmpty()) {
            throw new CustomException(RewardErrorCode.INVALID_REWARD_NAME);
        }
        this.userId = userId;
        this.rewardId = rewardId;
        this.acquireAt = LocalDateTime.now();
        this.status = Status.AVAILABLE;
        this.rewardSnapshot = new RewardSnapshot(rewardName, acquiredPrice, description);
    }

    void useReward() {
        if (status == Status.USED) {
            throw new CustomException(RewardErrorCode.ALREADY_USED);
        }
        this.status = Status.USED;
    }
}
