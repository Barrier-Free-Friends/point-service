package org.bf.pointservice.infrastructure.config;

import org.flywaydb.core.api.MigrationVersion;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
    public FlywayConfigurationCustomizer flywayConfigurationCustomizer() {
        return configuration -> {
            // target을 17 버전으로 명시적으로 설정
            configuration.target(MigrationVersion.fromVersion("17"));

            // 기타 베이스라인 설정 유지
            configuration.baselineOnMigrate(true);
            configuration.baselineVersion("0");
        };
    }
}
