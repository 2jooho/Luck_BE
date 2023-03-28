package com.example.luck_project.service;

import com.example.luck_project.common.util.PropertyUtil;
import com.example.luck_project.domain.CateDetailEntity;
import com.example.luck_project.domain.CateDetailImgEntity;
import com.example.luck_project.domain.UserPaymentEntity;
import com.example.luck_project.dto.CateDetailDto;
import com.example.luck_project.dto.response.CateListRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.CateDetailImgInfoRepository;
import com.example.luck_project.repository.CateDetailInfoRepository;
import com.example.luck_project.repository.UserPaymentInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.luck_project.constants.ResponseCode.*;


@Service
@Slf4j
public class CateService {

    @Autowired
    CateDetailInfoRepository cateDetailInfoRepository;

    @Autowired
    CateDetailImgInfoRepository cateDetailImgInfoRepository;

    @Autowired
    UserPaymentInfoRepository userPaymentInfoRepository;
    @Autowired
    private PropertyUtil propertyUtil;

    /**
     * 상세 카테고리 조회
     * @param reqMap
     * @param pageable
     * @return
     */
    @Transactional
    public CateListRes cateDetailList(Map<String, Object> reqMap, Pageable pageable){
        CateListRes cateListRes = new CateListRes();
        String userId = reqMap.get("userId").toString();
        String cateCode = reqMap.get("cateCode").toString();
        String rdnRstrcYn = "Y";    //열람제한 여부
        int rdnAvlblCnt = 0;
        LocalDateTime paymentStartDate = null;
        LocalDateTime paymentEndDate = null;

        log.info("[{}][{}] 사용자 결제 정보 조회", userId, cateCode);
        //사용자 결제 정보 조회
        Optional<UserPaymentEntity> userPaymentEntity = userPaymentInfoRepository.findByUserId(userId);
        userPaymentEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND_PAYMENT));


        //시간체크
        LocalDateTime nowDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        
        //사용자 열람 개수 체크
        rdnAvlblCnt = userPaymentEntity.get().getUseCnt() - userPaymentEntity.get().getUseCmplnCnt();
        if(rdnAvlblCnt > 0){
            rdnRstrcYn = "N";
        }
        //사용자 열람 결제 대상자 체크 - 제외
//        if(StringUtils.isNotBlank(String.valueOf(userPaymentEntity.get().getPaymentStart())) && StringUtils.isNotBlank(String.valueOf(userPaymentEntity.get().getPaymentEnd()))){
//            paymentStartDate = userPaymentEntity.get().getPaymentStart();
//            paymentEndDate = userPaymentEntity.get().getPaymentEnd();
//            if( (paymentStartDate.isBefore(nowDate) || paymentStartDate.isEqual(nowDate))
//                    && (paymentEndDate.isAfter(nowDate) || paymentEndDate.isEqual(nowDate))
//            ){
//                rdnRstrcYn = "N";
//            }
//        }

        log.info("[{}][{}] 카테고리 상세 정보 목록 조회", userId, cateCode);
        //카테고리 상세 정보 목록 조회
        Optional<List<CateDetailEntity>> cateDetailEntityList = Optional.of(cateDetailInfoRepository.findByCateCdOrderByInqryCntDescOrderNo(cateCode, pageable).get().getContent());
        cateDetailEntityList.orElseThrow(() -> new CustomException(CATE_DETAIL_INFO_FAIL));

        //카테고리 상세 코드 목록 설정
        List<String> cateDetailCdList = new ArrayList<>();
        cateDetailEntityList.get().stream().forEach((cateDetailEntity -> {
            cateDetailCdList.add(cateDetailEntity.getCateDetlCd());
        }));

        log.info("[{}][{}] 카테고리 상세 이미지 정보 조회", userId,cateCode);
        //카테고리 상세 이미지 정보 조회
        Optional<List<CateDetailImgEntity>> cateDetailImgEntityList = cateDetailImgInfoRepository.findByCateDetlCdInAndImageTypeAndImageoOrder(cateDetailCdList, "2", "1");
        cateDetailImgEntityList.orElseThrow(() -> new CustomException(BASIC_CATE_INFO_FAIL));

        //카테고리 상세 정보 설정
        List<CateDetailDto> cateDetailDtoList = new ArrayList<>();
        cateDetailEntityList.get().stream().forEach((cateDetailEntity) -> {
            CateDetailDto cateDetailDto = new CateDetailDto();
            cateDetailDto.setCateDetlCode(cateDetailEntity.getCateDetlCd());
            cateDetailDto.setCateDetlName(cateDetailEntity.getCateDetlName());
            cateDetailDto.setCateCntnt(cateDetailEntity.getCateDetlCntnt());
            //카테고리 상세 이미지 정보 설정
            cateDetailImgEntityList.get().stream().forEach((cateDetailImgEntity) -> {
              if(StringUtils.equals(cateDetailDto.getCateDetlCode(), cateDetailImgEntity.getCateDetlCd())){
                  cateDetailDto.setCateImgUrl(propertyUtil.getProperty("server.target.url") + cateDetailImgEntity.getFilePath() + cateDetailImgEntity.getFileName());
              }
                return;
            });
            cateDetailDtoList.add(cateDetailDto);
        });

        //응답 파라미터 설정
        cateListRes.setCateCode(cateCode);
        cateListRes.setPage(pageable.getPageSize());
        cateListRes.setRdnAvlblCnt(rdnAvlblCnt);
        cateListRes.setRdnRstrcYn(rdnRstrcYn);
//        if(rdnRstrcYn.equals("N")){
//            cateListRes.setPaymentStartDate(String.valueOf(paymentStartDate));
//            cateListRes.setPaymentEndDate(String.valueOf(paymentEndDate));
//        }
        cateListRes.setCateDetailList(cateDetailDtoList);

        return cateListRes;
    }

}
