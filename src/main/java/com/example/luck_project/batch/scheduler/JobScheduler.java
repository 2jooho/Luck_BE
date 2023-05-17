package com.example.luck_project.batch.scheduler;

import com.example.luck_project.batch.job.JdbcPagingJobConfiguration;
import com.example.luck_project.batch.job.JdbcTaskletJobConfiguration;
import com.example.luck_project.batch.job.SimpleChunkJobJpaConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private SimpleChunkJobJpaConfiguration simpleChunkJobConfiguration;
    @Autowired
    private JdbcPagingJobConfiguration jdbcPagingJobConfiguration;

    @Autowired
    private JdbcTaskletJobConfiguration jdbcTaskletJobConfiguration;

    /**
     * [청크] 매일 00시에 사용자별로 사용가능(관람) 개수 초기화
     */
//    @Scheduled(cron = "0 0/1 * * * ?") // 1분에 1번씩 실행 test용
    @Scheduled(cron = "0 0 0 * * ?") // 00시 00분에 한번 실행
    public void runJob() {

        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("day", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(jdbcPagingJobConfiguration.jdbcPagingItemReaderJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

            log.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @Scheduled(cron = "0 0/1 * * * ?") // 1분에 1번씩 실행
////    @Scheduled(cron = "0 2 0 * * ?") // 00시 02분에 한번 실행
//    public void runJob2() {
//
//        Map<String, JobParameter> confMap = new HashMap<>();
//        confMap.put("day2", new JobParameter(System.currentTimeMillis()));
//        JobParameters jobParameters = new JobParameters(confMap);
//
//        try {
//
//            jobLauncher.run(jdbcTaskletJobConfiguration.testTaskChunkJob(), jobParameters);
//
//        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
//                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
//
//            log.error(e.getMessage());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * [tasklet] 2시간 마다 fcm 푸시 발송
     */
//    @Scheduled(cron = "0 0/1 * * * ?") // 1분에 1번씩 실행 test용
    @Scheduled(cron = "0 0 1-23/2 * * ?") // 매달 매일 홀수시 실행
    public void runJob3() {

        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("every2hours", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {

            jobLauncher.run(jdbcTaskletJobConfiguration.testTaskChunkJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

            log.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
