package org.bf.pointservice.infrastructure.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableScheduling
public class BadgeUpdateScheduler {

    private final JobLauncher asyncJobLauncher;
    private final Job updateBadgeJob;

    /**
     * 뱃지 업데이트 스케줄러
     * - 새벽 3시에 주기적으로 작업 실행
     * */
    @Scheduled(cron = "0 0 3 * * *")
    public void updateBadge() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            asyncJobLauncher.run(updateBadgeJob, params);
            log.info("뱃지 업데이트 배치 작업이 시작되었습니다.");
        } catch (Exception e) {
            log.error("뱃지 업데이트 배치 작업 중 에러 발생 : ", e);
        }
    }
}
