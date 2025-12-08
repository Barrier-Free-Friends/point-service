package org.bf.pointservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bf.global.domain.Auditable;

import java.util.UUID;

/**
 * 누적 포인트에 따른 뱃지 정의
 * */
@Table(name = "p_badge")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID badgeId;

    private String badgeName;
    private long minPoints;
    private long maxPoints;
    private String descriptions;
    private String imgUrl;
}
