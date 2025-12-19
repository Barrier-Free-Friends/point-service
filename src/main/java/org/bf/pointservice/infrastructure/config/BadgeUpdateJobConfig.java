package org.bf.pointservice.infrastructure.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.bf.pointservice.domain.entity.point.PointBalance;
import org.bf.pointservice.domain.service.BadgeUpdateService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BadgeUpdateJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final BadgeUpdateService badgeUpdateService;

    @Bean
    public Job updateBadgeJob() {
        return new JobBuilder("updateBadgeJob", jobRepository)
                .start(updateBadgeStep())
                .build();
    }

    @Bean
    public Step updateBadgeStep() {
        return new StepBuilder("updateBadgeStep", jobRepository)
                .<PointBalance, PointBalance>chunk(100, transactionManager)
                .reader(pointBalanceReader())
                .processor(pointBalanceProcessor())
                .writer(pointBalanceWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<PointBalance> pointBalanceReader() {
        return new JpaPagingItemReaderBuilder<PointBalance>()
                .name("pointBalanceReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM PointBalance p")
                .pageSize(100)
                .build();
    }

    @Bean
    public ItemProcessor<PointBalance, PointBalance> pointBalanceProcessor() {
        return balance -> {
            badgeUpdateService.updateBadge(balance);
            return balance;
        };
    }

    @Bean
    public JpaItemWriter<PointBalance> pointBalanceWriter() {
        return new JpaItemWriterBuilder<PointBalance>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
