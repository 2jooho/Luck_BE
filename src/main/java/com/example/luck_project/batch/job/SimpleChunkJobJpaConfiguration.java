package com.example.luck_project.batch.job;

import com.example.luck_project.domain.UserPaymentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SimpleChunkJobJpaConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 100;

    @Bean
    public Job simpleChunkJob() {
        Job exampleJob = jobBuilderFactory.get("simpleChunkJob")
                .start(startStep())
                .build();
//        Job exampleJob = jobBuilderFactory.get("exampleJob")e
//                .start(startStep())
//                .on("FAILED") //startStep의 ExitStatus가 FAILED일 경우
//                .to(failOverStep()) //failOver Step을 실행 시킨다.
//                .on("*") //failOver Step의 결과와 상관없이
//                .to(writeStep()) //write Step을 실행 시킨다.
//                .on("*") //write Step의 결과와 상관없 이
//                .end() //Flow를 종료시킨다.
//
//                .from(startStep()) //startStep이 FAILED가 아니고
//                .on("COMPLETED") //COMPLETED일 경우
//                .to(processStep()) //process Step을 실행 시킨다
//                .on("*") //process Step의 결과와 상관없이
//                .to(writeStep()) // write Step을 실행 시킨다.
//                .on("*") //wrtie Step의 결과와 상관없이
//                .end() //Flow를 종료 시킨다.
//
//                .from(startStep()) //startStep의 결과가 FAILED, COMPLETED가 아닌
//                .on("*") //모든 경우
//                .to(writeStep()) //write Step을 실행시킨다.
//                .on("*") //write Step의 결과와 상관없이
//                .end() //Flow를 종료시킨다.
//                .end()
//                .build();


        return exampleJob;
    }

    @Bean
    @JobScope
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .<UserPaymentEntity, UserPaymentEntity> chunk(chunkSize)
                .reader(userPaymentEntityReader())
//                .processor(updateUserPaymentEntity())
                .writer(jpaPagingItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<UserPaymentEntity> userPaymentEntityReader() {
        log.info("userPaymentEntityReader");
        JpaPagingItemReader<UserPaymentEntity> reader = new JpaPagingItemReader<UserPaymentEntity>() {
            @Override
            public int getPage() {
                return 0;
            }
        };
        reader.setName("userPaymentEntityReader");
        reader.setQueryString("SELECT upe FROM UserPaymentEntity upe WHERE upe.useCmplnCnt < 5");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(chunkSize);
        return reader;
//        return new JpaPagingItemReaderBuilder<UserPaymentEntity>()
//                .name("userPaymentEntityReader")
//                .entityManagerFactory(entityManagerFactory)
//                .pageSize(chunkSize)
//                .queryString("SELECT upe FROM UserPaymentEntity upe WHERE upe.useCmplnCnt < 5")
//                .build();

//        또는 쿼리dsl로 조회
//                return new QuerydslPagingItemReader<testEntity>(entityManagerFactory, chunkSize, queryFactory -> queryFactory
//                .select(Projections.bean(testEntity.class,
//                        test.col.as(testEntity.col)
//                        ))
//                .from(test)
//                );

    }

//    @Bean
//    @StepScope
//    public ItemProcessor<UserPaymentEntity, UserPaymentEntity> updateUserPaymentEntity(){
//        return items -> {
////            log.info("updateUserPaymentEntity : {} / {} -> 5", item.getUserId(), item.getUseCmplnCnt());
////            items.forEach(
////                    item -> item.updateUserPayment(5, LocalDateTime.now(), "BATCH")
////            );
////
////            return item;
////        };
//    }

    @StepScope
    @Bean
    public ItemWriter<UserPaymentEntity> jpaPagingItemWriter() {
        return items -> {
            items.forEach(
                    item -> item.updateUserPayment(5, LocalDateTime.now(), "BATCH")
            );
            JpaItemWriter<UserPaymentEntity> jpaItemWriter = new JpaItemWriter<>();
            jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
            System.out.println("===== chunk is finished ====");
        };
    }

//    @Bean
//    @StepScope
//    public JpaItemWriter<UserPaymentEntity> jpaPagingItemWriter() {
//        JpaItemWriter<UserPaymentEntity> jpaItemWriter = new JpaItemWriter<>();
//        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
//        return jpaItemWriter;
//    }

}
