package com.example.luck_project.service;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.domain.UserLuckInfoEntity;
import com.example.luck_project.dto.MyLuckBtmDto;
import com.example.luck_project.dto.MyLuckTopDto;
import com.example.luck_project.dto.response.MyPageRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.UserInfoRepository;
import com.example.luck_project.repository.UserLuckInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.luck_project.constants.ResponseCode.USER_LUCK_NOT_FOUND;
import static com.example.luck_project.constants.ResponseCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {
    private final UserLuckInfoRepository userLuckInfoRepository;

    private final UserInfoRepository userInfoRepository;

    /**
     * 마이페이지 정보 조회
     * @param userId
     * @return
     */
    public MyPageRes getMyPageInfo(String userId){
        MyPageRes res = new MyPageRes();

//        Optional<UserEntity> userEntity = userInfoRepository.findByUserId(userId);
//        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
//
//        Optional<UserLuckInfoEntity> userLuckInfoEntity = userLuckInfoRepository.findByUserId(userId);
//        userLuckInfoEntity.orElseThrow(() -> new CustomException(USER_LUCK_NOT_FOUND));
//
//        //사주 이미지 테이블 조회
//
//        MyLuckTopDto myLuckTopDto = new MyLuckTopDto().builder()
//                .timeLuckImg()
//                .timeLuckKorean()
//                .timeLuckChinese()
//                .dayLuckImg()
//                .dayLuckKorean()
//                .dayLuckChinese()
//                .monthLuckImg()
//                .monthLuckKorean()
//                .monthLuckChinese()
//                .yearLuckImg()
//                .yearLuckKorean()
//                .yearLuckChinese()
//                .build();
//
//        MyLuckBtmDto myLuckBtmDto = new MyLuckBtmDto().builder()
//                .timeLuckImg()
//                .timeLuckKorean()
//                .timeLuckChinese()
//                .dayLuckImg()
//                .dayLuckKorean()
//                .dayLuckChinese()
//                .monthLuckImg()
//                .monthLuckKorean()
//                .monthLuckChinese()
//                .yearLuckImg()
//                .yearLuckKorean()
//                .yearLuckChinese()
//                .build();
//
//        recomendCode uuid
//        res.of(userId, userEntity.get().getBirth(), myLuckTopDto, myLuckBtmDto, );

        return res;
    }



}
