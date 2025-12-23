package org.bf.pointservice.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "p_policy_meta")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicyMeta {
    @Id
    private String id;

    @Column(name = "badge_version", nullable = false)
    private Long badgeVersion;

    // 버전 업데이트를 위한 편의 메서드
    public void updateVersion(Long newVersion) {
        this.badgeVersion = newVersion;
    }

    @Builder
    public PolicyMeta(String id, Long badgeVersion) {
        this.id = id;
        this.badgeVersion = badgeVersion;
    }
}
