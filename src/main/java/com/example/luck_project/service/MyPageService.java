package com.example.luck_project.service;

import com.example.luck_project.common.util.DataCode;
import com.example.luck_project.domain.LuckWordImgEntity;
import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.domain.UserLuckInfoEntity;
import com.example.luck_project.dto.MyLuckBtmDto;
import com.example.luck_project.dto.MyLuckTopDto;
import com.example.luck_project.dto.UserLuckInfoDto;
import com.example.luck_project.dto.response.MyPageRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.LuckWordImgRepository;
import com.example.luck_project.repository.UserInfoRepository;
import com.example.luck_project.repository.UserLuckInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.luck_project.common.util.CommonUtil.makeShortUUID;
import static com.example.luck_project.constants.ResponseCode.USER_LUCK_NOT_FOUND;
import static com.example.luck_project.constants.ResponseCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {
    private final UserLuckInfoRepository userLuckInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final LuckWordImgRepository luckWordImgRepository;

    /**
     * 마이페이지 정보 조회
     * @param userId
     * @return
     */
    public MyPageRes getMyPageInfo(String userId){
        MyPageRes res = new MyPageRes();

        Optional<UserEntity> userEntity = userInfoRepository.findByUserId(userId);
        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        //사주 정보 및 이미지 조회
        Optional<UserLuckInfoDto> userLuckInfoDto = userLuckInfoRepository.serchUserLuckInfo(userId);
        userLuckInfoDto.orElseThrow(()-> new CustomException(USER_LUCK_NOT_FOUND));

        MyLuckTopDto myLuckTopDto = new MyLuckTopDto().builder()
                .timeLuckImg(userLuckInfoDto.get().getTimeTopImg())
                .timeLuckKorean(userLuckInfoDto.get().getKoTimeTop())
                .timeLuckChinese(userLuckInfoDto.get().getChTimeTop())
                .dayLuckImg(userLuckInfoDto.get().getDayTopImg())
                .dayLuckKorean(userLuckInfoDto.get().getKoDayTop())
                .dayLuckChinese(userLuckInfoDto.get().getChDayTop())
                .monthLuckImg(userLuckInfoDto.get().getMonthTopImg())
                .monthLuckKorean(userLuckInfoDto.get().getKoMonthTop())
                .monthLuckChinese(userLuckInfoDto.get().getChMonthTop())
                .yearLuckImg(userLuckInfoDto.get().getYearTopImg())
                .yearLuckKorean(userLuckInfoDto.get().getKoYearTop())
                .yearLuckChinese(userLuckInfoDto.get().getChYearTop())
                .build();

        MyLuckBtmDto myLuckBtmDto = new MyLuckBtmDto().builder()
                .timeLuckImg(userLuckInfoDto.get().getTimeBtmImg())
                .timeLuckKorean(userLuckInfoDto.get().getKoTimeBtm())
                .timeLuckChinese(userLuckInfoDto.get().getChTimeBtm())
                .dayLuckImg(userLuckInfoDto.get().getDayBtmImg())
                .dayLuckKorean(userLuckInfoDto.get().getKoDayBtm())
                .dayLuckChinese(userLuckInfoDto.get().getChDayBtm())
                .monthLuckImg(userLuckInfoDto.get().getMonthBtmImg())
                .monthLuckKorean(userLuckInfoDto.get().getKoMonthBtm())
                .monthLuckChinese(userLuckInfoDto.get().getChMonthBtm())
                .yearLuckImg(userLuckInfoDto.get().getYearBtmImg())
                .yearLuckKorean(userLuckInfoDto.get().getKoYearBtm())
                .yearLuckChinese(userLuckInfoDto.get().getChYearBtm())
                .build();

        res.of(userId, userEntity.get().getBirth(), myLuckTopDto, myLuckBtmDto, userEntity.get().getRecommandCode());

        return res;
    }

}
