package org.bf.pointservice.infrastructure.persistence.repository;

import org.bf.pointservice.infrastructure.persistence.entity.PolicyMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyMetaRepository extends JpaRepository<PolicyMeta, String> {
}
