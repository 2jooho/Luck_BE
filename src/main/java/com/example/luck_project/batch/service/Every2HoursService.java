package com.example.luck_project.batch.service;


import com.example.luck_project.batch.util.DataCode;
import com.example.luck_project.domain.BasicDateEntity;
import com.example.luck_project.repository.BasicDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class Every2HoursService {
    private final BasicDateRepository basicDateRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<BasicDateEntity> setBasicDate(Optional<BasicDateEntity> basicDateEntity, LocalDate localDateToday, String todayDate) {
        int basicYear = Integer.valueOf(basicDateEntity.get().getBasicDate().substring(0,4));
        int basicMonth = Integer.valueOf(basicDateEntity.get().getBasicDate().substring(5,7));
        int basicDay = Integer.valueOf(basicDateEntity.get().getBasicDate().substring(8,10));
        Long minusDate = ChronoUnit.DAYS.between(LocalDate.of(basicYear, basicMonth, basicDay), localDateToday);
//        int minusDate = Integer.valueOf(todayDate) - Integer.valueOf(basicDate.replaceAll("-", ""));
        String basicVersYear = basicDateEntity.get().getVersYearInfo();
        String versYear = "";
        Optional<Integer> codeNum = Optional.of(DataCode.getCodeNum(DataCode.VERS_YEAR_NAME_ARR, basicVersYear));
        log.info("오늘 날짜 띠 계산");
        int todayVersYear = codeNum.get() + minusDate.intValue();
        while (true) {
            if (todayVersYear > 12) {
                todayVersYear = todayVersYear - 12;
            } else {
                versYear = DataCode.VERS_YEAR_NAME_ARR[todayVersYear - 1];
                break;
            }
        }
        BasicDateEntity basicDateEntitybuild = BasicDateEntity.builder()
                .basicDate(todayDate.substring(0,4)+"-"+todayDate.substring(5,7)+"-"+todayDate.substring(8,10))
                .versYearInfo(versYear)
                .build();
        basicDateRepository.save(basicDateEntitybuild);

        return Optional.of(basicDateEntitybuild);
    }

    public String getNowTimeLuck(String todayLuck,  String todayTimeDate) {
        String nowPureLuck = "";
        int cnt = 0;
        //시간별 운은 날운에 따라서 다르다.
        //날운이 '인'이면 현재시간이 21시 5분이면 '해'이다. 인날의 해는 해결신이다.
        String[][] timeArr = DataCode.VERS_YEAR_TIME_ARR;
        int todayTime = Integer.valueOf(todayTimeDate);

        for(String[] arr : timeArr){
            cnt++;
            if(Integer.valueOf(arr[0]) <= todayTime && todayTime <= Integer.valueOf(arr[1])){
                int versYearNameNum = DataCode.getCodeNum(DataCode.VERS_YEAR_NAME_ARR, todayLuck); //오늘 띠의 위치번호
                log.info("versYearNameNum:{}", versYearNameNum);
                int nowPureLuckNum = 0;
                if(cnt < versYearNameNum){
                    nowPureLuckNum = ((cnt+12)-versYearNameNum);
                }else{
                    nowPureLuckNum = (cnt-versYearNameNum);
                }
                log.info("nowPureLuckNum:{}", nowPureLuckNum);
                nowPureLuck = DataCode.PURE_LUCK_NAME_ARR[nowPureLuckNum];
                break;
            }
        }

        return nowPureLuck;
    }

}
