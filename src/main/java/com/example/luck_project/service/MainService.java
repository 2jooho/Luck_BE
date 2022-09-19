package com.example.luck_project.service;

import com.example.luck_project.domain.MyFortuneInfoEntity;
import com.example.luck_project.domain.TodayLuckEntity;
import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.MainRes;
import com.example.luck_project.repository.MyFortuneInfoRepository;
import com.example.luck_project.repository.TodayLuckInfoRepository;
import com.example.luck_project.repository.UserInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MainService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private TodayLuckInfoRepository todayLuckInfoRepository;
    @Autowired
    private MyFortuneInfoRepository myFortuneInfoRepository;

    public TodayLuckEntity main(String userId) {
        MainRes mainRes = new MainRes();


        //테스트용 데이터 삽입
        UserEntity u1 = new UserEntity("2week", "이주호", "19940528", "154200", null);
        List<UserEntity> uList = Arrays.asList(u1);
        userInfoRepository.saveAll(uList);
        //테스트용 데이터 사용자 운정보
        MyFortuneInfoEntity mf1 = new MyFortuneInfoEntity("2week", "땅", "갑", null);
        myFortuneInfoRepository.save(mf1);
        //테스트용 오늘의 운세
        TodayLuckEntity tlu1 = new TodayLuckEntity("2week", "땅", "갑", "70", "무난해요 ㅎㅎ");
        todayLuckInfoRepository.save(tlu1);


        //사용자 오늘 운세 조회 로직 진행
        Optional<UserEntity> userEntity = userInfoRepository.findByUserId(userId);
        log.info("[{}] 사용자 정보 조회 : {}", userId, userEntity);

        Optional<MyFortuneInfoEntity> myFortuneInfoEntity = myFortuneInfoRepository.findByUserId(userId);
        log.info("[{}] 사용자 정보 조회 : {}", userId, myFortuneInfoEntity);
        Optional<TodayLuckEntity> todayLuckEntity = todayLuckInfoRepository.findByUserIdAndYearLuckKrAndDayLuckKr(userId, myFortuneInfoEntity.get().getYearLuckKr(), myFortuneInfoEntity.get().getDayLuckKr());

        //사용자 추천 카테고리 조회

        //카테고리 조회

        //운세 캘린더 정보 조회



        return todayLuckEntity.get();
    }
}
