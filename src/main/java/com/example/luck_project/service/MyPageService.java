package com.example.luck_project.service;

import com.example.luck_project.dto.response.MyPageRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    /**
     * 마이페이지 정보 조회
     * @param userId
     * @return
     */
    public MyPageRes getMyPageInfo(String userId){
        MyPageRes res = new MyPageRes();
        return res;
    }



}
