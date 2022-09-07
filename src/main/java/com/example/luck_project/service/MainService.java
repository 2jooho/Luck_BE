package com.example.luck_project.service;

import com.example.luck_project.domain.MyFortuneInfoEntity;
import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.MainRes;
import com.example.luck_project.dto.TodayLuckInfoDto;
import com.example.luck_project.repository.TodayLuckInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MainService {

    @Autowired
    private TodayLuckInfoRepository todayLuckInfoRepository;

    public MainRes main(String userId) {
        //테스트용 데이터 삽입
        MyFortuneInfoEntity my1 = new MyFortuneInfoEntity();
        UserEntity u1 = new UserEntity("2week", "이주호", "19940528", "154200", null);
        List<UserEntity> uList = Arrays.asList(u1);

        todayLuckInfoRepository.saveAll(uList);

        MainRes mainRes = new MainRes();
        //사용자 오늘 운세 조회
        List<UserEntity> userEntity = todayLuckInfoRepository.findAll();
        mainRes.setUserId(userEntity.get(0).getUserId());
        log.info("사용자 정보 조회" + userEntity);
        System.out.println("test:" + userEntity);
        //사용자 오늘 운세


        return mainRes;
    }
}
