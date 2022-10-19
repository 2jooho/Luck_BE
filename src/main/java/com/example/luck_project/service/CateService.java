package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.util.PropertyUtil;
import com.example.luck_project.domain.CateDetailEntity;
import com.example.luck_project.domain.CateDetailImgEntity;
import com.example.luck_project.dto.CateDetailDto;
import com.example.luck_project.dto.CateListRes;
import com.example.luck_project.dto.RecommandCateDto;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.CateDetailImgInfoRepository;
import com.example.luck_project.repository.CateDetailInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.luck_project.exception.ErrorCode.BASIC_CATE_INFO_FAIL;
import static com.example.luck_project.exception.ErrorCode.CATE_DETAIL_INFO_FAIL;

@Service
public class CateService extends ApiSupport {

    @Autowired
    CateDetailInfoRepository cateDetailInfoRepository;

    @Autowired
    CateDetailImgInfoRepository cateDetailImgInfoRepository;

    @Autowired
    private PropertyUtil propertyUtil;

    /**
     * 상세 카테고리 조회
     * @param reqMap
     * @param pageable
     * @return
     */
    public CateListRes cateDetailList(Map<String, Object> reqMap, Pageable pageable){
        CateListRes cateListRes = new CateListRes();
        String cateCode = reqMap.get("cateCode").toString();

        logger.info("[{}] 카테고리 상세 정보 목록 조회", cateCode);
        //카테고리 상세 정보 목록 조회
        Optional<List<CateDetailEntity>> cateDetailEntityList = Optional.of(cateDetailInfoRepository.findByCateCdOrderByInqryCntDescOrderNo(cateCode, pageable).get().getContent());
        cateDetailEntityList.orElseThrow(() -> new CustomException(CATE_DETAIL_INFO_FAIL));

        //카테고리 상세 코드 목록 설정
        List<String> cateDetailCdList = new ArrayList<>();
        cateDetailEntityList.get().stream().forEach((cateDetailEntity -> {
            cateDetailCdList.add(cateDetailEntity.getCateDetlCd());
        }));

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
        cateListRes.setCateDetailList(cateDetailDtoList);

        return cateListRes;
    }






    /**
     * CRLF 개행제거
     */
    public String strCRLF(Object obj) {
        String retStr= null;

        if(obj != null) {
            if(obj instanceof Throwable) {
                retStr = ExceptionUtils.getStackTrace((Throwable) obj).replaceAll("\r\n", "");
            } else {
                retStr = obj.toString().replaceAll("\r\n", "");
            }
        }

        return retStr;
    }

}
