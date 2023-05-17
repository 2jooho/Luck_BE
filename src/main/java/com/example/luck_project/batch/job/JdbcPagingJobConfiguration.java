package com.example.luck_project.batch.job;

import com.example.luck_project.domain.UserPaymentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class JdbcPagingJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource; // DataSource DI

    private static final int chunkSize = 10;

    /**
     * 청크 시작 job
     * @return
     * @throws Exception
     */
    @Bean
    public Job jdbcPagingItemReaderJob() throws Exception {
        return jobBuilderFactory.get("jdbcPagingItemReaderJob")
                .start(jdbcPagingItemReaderStep())
                .build();
    }

    /**
     * 첫번째 step
     * 데이터를 10개씩 읽어서
     * @return
     * @throws Exception
     */
    @Bean
    public Step jdbcPagingItemReaderStep() throws Exception {
        return stepBuilderFactory.get("jdbcPagingItemReaderStep")
                .<UserPaymentEntity, UserPaymentEntity>chunk(chunkSize)
                .reader(jdbcPagingItemReader())
                .processor(itemProcessor())
                .writer(jdbcPagingItemWriter())
                .build();
    }

    /**
     * 읽기
     * @return
     * @throws Exception
     */
    @Bean
    public JdbcPagingItemReader<UserPaymentEntity> jdbcPagingItemReader() throws Exception {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("RESET_USE_CNT", 0);
        parameters.put("STATUS", "C");

        return new JdbcPagingItemReaderBuilder<UserPaymentEntity>()
                .fetchSize(chunkSize)    //10개 씩 조회
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(UserPaymentEntity.class))
                .queryProvider(createQueryProvider())
                .parameterValues(parameters)
//                .sql("SELECT upe FROM UserPaymentEntity upe WHERE upe.useCmplnCnt < 5")
                .name("jdbcPagingItemReader")
                .build();
    }

    /**
     * 처리
     * @return
     */
    @Bean
    public ItemProcessor<UserPaymentEntity, UserPaymentEntity> itemProcessor() {
        return item -> {
            log.info("item1:{}/{}/{}",item.getUserId(), item.getUseCmplnCnt());
            item.updateUserPayment(0, LocalDateTime.now(), "BATCH");
            log.info("item2:{}/{}/{}",item.getUserId(), item.getUseCmplnCnt());
            return item;
        };
    }

    /**
     * 쓰기
     * @return
     */
    @Bean
    public JdbcBatchItemWriter<UserPaymentEntity> jdbcPagingItemWriter() {

        return new JdbcBatchItemWriterBuilder<UserPaymentEntity>()
                .dataSource(dataSource)
                .sql("UPDATE lck_user_payament_status SET USE_CMPLN_CNT = :useCmplnCnt, EDIT_DTM = :editDtm, UPUS_ID = :upusId WHERE USER_ID = :userId")
                .beanMapped()
                .build();

//        return items -> {
//            for (UserPaymentEntity item : items) {
//                System.out.println(item.toString());
//            }
//        };
//        return new JdbcBatchItemWriterBuilder<UserPaymentEntity>()
//                .dataSource(dataSource)
//                .sql("UPDATE UserPaymentEntity SET useCmplnCnt = 5 WHERE userId = :userId")
//                .beanMapped()
//                .build();
    }

    @Bean
    public PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource); // Database에 맞는 PagingQueryProvider를 선택하기 위해
        queryProvider.setSelectClause("USER_ID, USE_CNT, USE_CMPLN_CNT");
        queryProvider.setFromClause("FROM lck_user_payament_status");
        queryProvider.setWhereClause("WHERE USE_CMPLN_CNT > :RESET_USE_CNT AND STATUS = :STATUS");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("USER_ID", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        return queryProvider.getObject();
    }

}
