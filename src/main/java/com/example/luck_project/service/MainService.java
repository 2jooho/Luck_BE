package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.domain.CateEntity;
import com.example.luck_project.domain.PureInfoEntity;
import com.example.luck_project.dto.CateDto;
import com.example.luck_project.dto.CateImgDto;
import com.example.luck_project.dto.MainRes;
import com.example.luck_project.dto.UserInfoDto;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.CateInfoRepository;
import com.example.luck_project.repository.PureInfoRepository;
import com.example.luck_project.repository.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.luck_project.exception.ErrorCode.*;

@Service
public class MainService extends ApiSupport {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PureInfoRepository pureInfoRepository;

    @Autowired
    private CateInfoRepository cateInfoRepository;

    @Autowired(required = true)
    private transient Environment env;


    @Transactional(readOnly = true)
    public MainRes main(String userId) {
        MainRes mainRes = new MainRes();

//        Optional<UserLuckInfoEntity> userLuckInfoEntity = userLuckInfoRepository.findByUserId(userId);
//        log.info("[{}] 사용자 운세 정보 조회 : {}", userId, userLuckInfoEntity.get());

        //하단 내용은 향후 메소드화 시켜야함
        logger.info("[{}] 사용자 정보 조회", userId);
        Optional<UserInfoDto> userInfoDto = userInfoRepository.searchAll(userId);
        //사용자 정보가 없는 경우 Exception
        userInfoDto.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String ydPureCnctn = userInfoDto.get().getYearBtm() + userInfoDto.get().getDayBtm();  //년지,월지 조합
        String dyPureCnctn =  userInfoDto.get().getDayBtm() + userInfoDto.get().getYearBtm(); //월지,년지 조합

        //비장술 운세 정보 조회
        logger.info("[{}][{}] 비장술 운세 정보 조회", userId, ydPureCnctn);
        List<String> pureCnctnList = new ArrayList<>();
        pureCnctnList.add(ydPureCnctn);
        pureCnctnList.add(dyPureCnctn);

        Optional<PureInfoEntity> pureInfoEntity = pureInfoRepository.findByPureCnctnIn(pureCnctnList);
        //비장술 운세 정보가 없는 경우 Exception
        pureInfoEntity.orElseThrow(() -> new CustomException(PURE_LUCK_INFO_FAIL));

        //추천 카테고리 조회

        //카테고리 및 카테고리 이미지 정보 조회
        logger.info("[{}] 카테고리 정보 조회", userId);
        Optional<List<CateEntity>> cateEntityList= cateInfoRepository.findByUseYnOrderByOrderNo("Y");
        cateEntityList.get().stream().forEachOrdered(cateEntities -> logger.info(cateEntities));
        //카테고리 정보가 존재하지 않는 경우 Exception
        cateEntityList.orElseThrow(() -> new CustomException(BASIC_CATE_INFO_FAIL));

        //카테고리 정보 설정
        List<CateDto> cateDtoList = new ArrayList<>();

        cateEntityList.get().stream().forEach((cateEntity) -> {
            CateDto cateDto = new CateDto();
            CateImgDto cateImgDto = new CateImgDto();

            cateDto.setCateCode(cateEntity.getCateCode());
            cateDto.setCateName(cateEntity.getCateName());
            cateEntity.getCateImgEntity().stream().forEach((cateImgEntity)-> {
                if(StringUtils.equals("1",cateImgEntity.getImageoOrder())) {
                    cateImgDto.setImgUrl(env.getProperty("server.target.url") + cateImgEntity.getFilePath() + cateImgEntity.getFileName());
                    cateImgDto.setImgType(cateImgEntity.getImageType());
                    cateDto.setCateImgDto(cateImgDto);
                    return;
                }
            });
            cateDtoList.add(cateDto);
        });

        //응답 파라미터 설정
        mainRes.setNickName(userInfoDto.get().getNickName());
        mainRes.setPureCnctn(ydPureCnctn);
        mainRes.setPureCntnt(pureInfoEntity.get().getPureCntnt());
        mainRes.setCharactorFlag(pureInfoEntity.get().getCharactorFlag());
        mainRes.setCateDtoList(cateDtoList);
        return mainRes;
    }

}
