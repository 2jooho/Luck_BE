package com.example.luck_project.batch.job;

import com.example.luck_project.batch.entity.FcmAuthEntity;
import com.example.luck_project.batch.entity.PushMessageEntity;
import com.example.luck_project.batch.repository.FcmAuthRepository;
import com.example.luck_project.batch.repository.PushMessageRepository;
import com.example.luck_project.batch.service.Every2HoursService;
import com.example.luck_project.batch.service.FirebaseCloudMessageService;
import com.example.luck_project.domain.BasicDateEntity;
import com.example.luck_project.repository.BasicDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class JdbcTaskletJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BasicDateRepository basicDateRepository;
    private final Every2HoursService every2HoursService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final FcmAuthRepository fcmAuthRepository;

    private final PushMessageRepository pushMessageRepository;

    @Bean
    public Job testTaskChunkJob() throws Exception {
        return jobBuilderFactory.get("testTaskChunkJob")
                .start(taskStep1())
                .build();
    }

    @Bean
    public Step taskStep1() throws Exception {
        return stepBuilderFactory.get("taskStep1")
                .tasklet(tasklet())
                .build();
    }

    private Tasklet tasklet() { //tasklet으로 모두 처리
        return (contribution, chunkContext) -> {
            boolean items = getItems();

            System.out.printf("items:" + items);

            return RepeatStatus.FINISHED;
        };
    }

    private boolean getItems() {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDate localDateToday = LocalDate.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String todayDate = today.format(formatter);
        String nowTime = today.format(timeFormatter);

        log.info("오늘 날짜 띠 조회");
        //오늘 날짜 띠 조회
        Optional<BasicDateEntity> basicDateEntity = basicDateRepository.findTop1ByOrderByBasicDateDesc();
        //db에 있는 날짜와 오늘날짜가 같지 않으면 오늘 날짜 띠 구하는 로직
        if (basicDateEntity.isPresent()) {
            if(!StringUtils.equals(basicDateEntity.get().getBasicDate(), todayDate)){
                basicDateEntity = every2HoursService.setBasicDate(basicDateEntity, localDateToday, todayDate);
            }
        }else {
            BasicDateEntity basicDateEntitybuild = BasicDateEntity.builder()
                    .basicDate("2023-03-21")
                    .versYearInfo("인")
                    .build();
            basicDateEntity = every2HoursService.setBasicDate(Optional.of(basicDateEntitybuild), localDateToday, todayDate);
        }
        log.info("오늘 날짜 현재 시간 운 찾기 -> {}", nowTime);

        //오늘 날짜에 현재 시간 운 찾기
        String nowTimeLuck = "";
        nowTimeLuck = every2HoursService.getNowTimeLuck(basicDateEntity.get().getVersYearInfo(), nowTime);
        log.info("nowTimeLuck: {}, nowTime: {}",nowTimeLuck, nowTime);

        // jpa로 진행
        //토픽 정보 조회
        Optional<FcmAuthEntity> fcmAuthEntity =  fcmAuthRepository.findTop1ByOrderByIdDesc();
        Optional<PushMessageEntity> pushMessageEntity =  pushMessageRepository.findByTimeLuck(nowTimeLuck);

        if(!fcmAuthEntity.isPresent() || !pushMessageEntity.isPresent()){
            return false;
        }

        String target = fcmAuthEntity.get().getTopicCode();
        String title = pushMessageEntity.get().getTitleMsg();
        String message = pushMessageEntity.get().getMainMsg();
        log.info("토픽: {}, 타이틀: {}, 메시지 : {}", target, title, message);

        //토픽 정보 조회
        //오늘 날짜 띠 기준 현재 시간 비장술 정보 조회
        //당일 비장술 + 시간 비장술 조합하여 title, body 문구 생성 or 조회
        //Fcm 푸시 로직 진행
        try {
            log.info("[{}][{}] FCM 푸시 로직 진행", basicDateEntity.get().getBasicDate(), basicDateEntity.get().getVersYearInfo());
            firebaseCloudMessageService.sendMessageTo(target, title, message);
        }catch (Exception e){
            log.info("[{}]fcm 푸시 발생 오류 : ", target, e);
            return false;
        }

        return true;
    }

}
