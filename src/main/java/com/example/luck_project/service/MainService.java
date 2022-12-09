package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.util.DataCode;
import com.example.luck_project.common.util.PropertyUtil;
import com.example.luck_project.domain.*;
import com.example.luck_project.dto.*;
import com.example.luck_project.dto.response.MainRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.luck_project.exception.ErrorCode.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class MainService extends ApiSupport {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PureInfoRepository pureInfoRepository;

    @Autowired
    private CateInfoRepository cateInfoRepository;

    @Autowired
    private CateImgInfoRepository cateImgInfoRepository;

    @Autowired
    private CateDetailInfoRepository cateDetailInfoRepository;

    @Autowired
    private CateDetailImgInfoRepository cateDetailImgInfoRepository;

    @Autowired
    private UserPureCombRepository userPureCombRepository;

    @Autowired
    private BasicDateRepository basicDateRepository;

    @Autowired
    private PropertyUtil propertyUtil;


    @Transactional(readOnly = true)
    public MainRes main(String userId) {
        MainRes mainRes = new MainRes();
        String pureCnctn = ""; //비장술 조합

        //하단 내용은 향후 메소드화 시켜야함
        logger.info("[{}] 사용자 정보 조회", userId);
        //사용자의 사주정보(년지월지)를 조회
        Optional<UserInfoDto> userInfoDto = userInfoRepository.serchUserInfo(userId);
        //사용자 정보가 없는 경우 Exception
        userInfoDto.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String ydPureCnctn = userInfoDto.get().getYearBtm() + userInfoDto.get().getDayBtm();  //년지,월지 조합
        String dyPureCnctn =  userInfoDto.get().getDayBtm() + userInfoDto.get().getYearBtm(); //월지,년지 조합

        //사용자 관심 카테고리 목록 설정
        List<String> userCateCdList = Stream.of(userInfoDto.get().getCateCodeList().split(","))
                        .collect(Collectors.toList());

        //비장술 운세 정보 조회
        logger.info("[{}][{}] 비장술 운세 정보 조회", userId, ydPureCnctn);

        //오늘 날짜 띠 정보 조회
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = today.format(formatter);

        //기본으로 들어있는 값중 최근 것을 조회하여 날짜 정보를 얻는데 사용 => insert는 00시에 배치가 돌지만 1개만 존재해도 구할 수 있음
        Optional<BasicDateEntity> basicDateEntity = basicDateRepository.findTop1ByOrderByBasicDateDesc();
        basicDateEntity.orElseThrow(() -> new CustomException(BASIC_DATE_NOT_FOUND));

        String versYear = ""; //띠 정보

        logger.info("[{}][{}][{}] 기준날짜 띠 찾기", userId, ydPureCnctn, basicDateEntity.get().getBasicDate());
        //기준 날짜와 오늘 날짜가 같지 않은 경우 오늘 날짜 띠 찾기
        //20221011,유 -> 신
        String basicDate = basicDateEntity.get().getBasicDate();
        if(!StringUtils.equals(basicDate, todayDate)){
            LocalDate dateBefore = LocalDate.parse(basicDate); //형식 : 2022-10-11
            LocalDate dateAfter = LocalDate.parse(todayDate);
            long minusDate = DAYS.between(dateBefore, dateAfter);
            String basicVersYear = basicDateEntity.get().getVersYearInfo();

            Optional<Integer> codeNum = Optional.of(DataCode.getCodeNum(DataCode.VERS_YEAR_NAME_ARR, basicVersYear));
            codeNum.orElseThrow(() -> new CustomException(BASIC_DATE_NOT_FOUND));

            int todayVersYear = codeNum.get() + (int)minusDate;
            while(true) {
                if (todayVersYear > 12) {
                    int forCnt = todayVersYear/12;
                    todayVersYear = todayVersYear - (forCnt*12);
                } else {
                    versYear = DataCode.VERS_YEAR_NAME_ARR[todayVersYear-1];
                    break;
                }
            }
        }else{
            versYear = basicDateEntity.get().getBasicDate();
        }

        // 사주정보 목록
        List<String> luckCnctnList = new ArrayList<>();
        luckCnctnList.add(ydPureCnctn);
        luckCnctnList.add(dyPureCnctn);

        logger.info("[{}][{}][{}] 비장술 조합 정보 조회", userId, versYear, basicDateEntity.get().getBasicDate());
        //비장술 정보 조회
        Optional<UserPureCombinationEntity> pureCombinationEntity = userPureCombRepository.findTop1ByLuckCnctnInAndVersYear(luckCnctnList, versYear);
        pureCombinationEntity.orElseThrow(() -> new CustomException(PURE_LUCK_INFO_FAIL));

        // 비장술 정보 목록
        List<String> pureCnctnList = new ArrayList<>();
        pureCnctn = pureCombinationEntity.get().getPureYear() + "," + pureCombinationEntity.get().getPureDay();
        pureCnctnList.add(pureCnctn);
        pureCnctnList.add(pureCombinationEntity.get().getPureDay() + "," + pureCombinationEntity.get().getPureYear());

        logger.info("[{}][{}][{}] 비장술 정보 조회", userId, versYear, basicDateEntity.get().getBasicDate());
        //ex. 강일진,해결신의 운세
        Optional<PureInfoEntity> pureInfoEntity = pureInfoRepository.findByPureCnctnIn(pureCnctnList);
        //비장술 운세 정보가 없는 경우 Exception
        pureInfoEntity.orElseThrow(() -> new CustomException(PURE_LUCK_INFO_FAIL));

        //추천 카테고리 조회
        logger.info("[{}] 추천 카테고리 정보 조회", userId);
        //사용자가 선택한 정보 중 광고가 붙은 카테고리 8개, 만약, 광고가 붙은 카테고리가 없는 경우 상세카테고리 조회수 총 합이 가장 큰 순으로 8개 단 광고가 붙어야함 만약 없으면 상세카테고리 조회수 많은거로
        //사용자가 선택한 정보 중 광고가 붙은 카테고리 8개
        //아우터 조인을 해야하나..
        //상세 조회에서 in절로 해당 카테 아우터 조인, 광고 y인거 아우터 조인, 이걸 서브쿼리 in 아투터 조인 조회수 순으로
        //사용자가 선택한 카테고리 중 광고가 붙은 카테고리 조회
        Optional<List<CateDetailEntity>> cateDetailEntityList = cateDetailInfoRepository.findTop8ByUseYnAndAdYnAndCateCdInOrderByInqryCntDesc("Y", "Y", userCateCdList);
        List<CateDetailEntity> concatCateDetailEntityList = new ArrayList<>();
        //추천 카테고리 8개 이상인지 체크
        if(cateDetailEntityList.stream().count() < 8){
            //광고가 존재하고 조회수가 높은 카테고리 조회
            Optional<List<CateDetailEntity>> basicCateDetailEntityList = cateDetailInfoRepository.findTop8ByUseYnOrderByAdYnDescInqryCntDesc("Y");

            concatCateDetailEntityList = Stream.of(cateDetailEntityList.get(), basicCateDetailEntityList.get())
                .flatMap(x -> x.stream()).limit(8).distinct()
                .collect(Collectors.toList());
        }else {
            concatCateDetailEntityList.addAll(cateDetailEntityList.get());
        }

        List<String> cateDetailCdList = new ArrayList<>();
        concatCateDetailEntityList.stream().forEach((cateDetailEntity -> {
            cateDetailCdList.add(cateDetailEntity.getCateDetlCd());
        }));

        //추천 카테고리 이미지 조회
        Optional<List<CateDetailImgEntity>> cateDetailImgEntityList = cateDetailImgInfoRepository.findByCateDetlCdInAndImageTypeAndImageoOrder(cateDetailCdList, "2", "1");
        cateDetailImgEntityList.orElseThrow(() -> new CustomException(BASIC_CATE_INFO_FAIL));

        //추천 카테고리 설정
        List<RecommandCateDto> recommandCateDtoList = new ArrayList<>();
        AtomicInteger rank = new AtomicInteger(1);
        concatCateDetailEntityList.stream().forEach((cateDetailEntity) -> {
            RecommandCateDto recommandCateDto = new RecommandCateDto();
            recommandCateDto.setCateCode(cateDetailEntity.getCateDetlCd());
            recommandCateDto.setCateNmae(cateDetailEntity.getCateDetlName());
            recommandCateDto.setCateCntnt(cateDetailEntity.getCateDetlCntnt());
            recommandCateDto.setRank(String.valueOf(rank));
            //추천 카테고리 이미지 설정
            cateDetailImgEntityList.get().stream().forEach((cateDetlImgEntity) -> {
                if(StringUtils.equals(cateDetlImgEntity.getCateDetlCd(), cateDetailEntity.getCateDetlCd())){
                    recommandCateDto.setCateImgUrl(propertyUtil.getProperty("server.target.url") + cateDetlImgEntity.getFilePath() + cateDetlImgEntity.getFileName());
                    return;
                }
            });

            recommandCateDtoList.add(recommandCateDto);
            rank.getAndIncrement();
        });

        //카테고리 및 카테고리 이미지 정보 조회
        logger.info("[{}] 카테고리 정보 조회", userId);
        Optional<List<CateEntity>> cateEntityList= cateInfoRepository.findByUseYn("Y");

        //카테고리 정보가 존재하지 않는 경우 Exception
        cateEntityList.orElseThrow(() -> new CustomException(BASIC_CATE_INFO_FAIL));

        //카테고리 코드 목록 설정
        List<String> cateCdList = new ArrayList<>();
        cateEntityList.get().stream().forEach((cateEntity) -> {
            cateCdList.add(cateEntity.getCateCode());
        });

        //카테고리 이미지 조회
        Optional<List<CateImgEntity>> cateImgEntityList = cateImgInfoRepository.findByCateCodeInAndImageTypeAndImageoOrder(cateCdList, "1" ,"1");

        cateImgEntityList.orElseThrow(() -> new CustomException(BASIC_CATE_INFO_FAIL));

        //카테고리 정보 설정
        List<CateDto> cateDtoList = new ArrayList<>();

        cateEntityList.get().stream().forEach((cateEntity) -> {
            CateDto cateDto = new CateDto();
            CateImgDto cateImgDto = new CateImgDto();

            cateDto.setCateCode(cateEntity.getCateCode());
            cateDto.setCateName(cateEntity.getCateName());
            //카테고리 이미지 정보 설정
            cateImgEntityList.get().stream().forEach((cateImgEntity) -> {
                if(StringUtils.equals(cateEntity.getCateCode(), cateImgEntity.getCateCode())){
                    cateImgDto.setImgUrl(propertyUtil.getProperty("server.target.url") + cateImgEntity.getFilePath() + cateImgEntity.getFileName());
                    cateImgDto.setImgType(cateImgEntity.getImageType());
                    cateDto.setCateImgDto(cateImgDto);
                    return;
                }
            });
            cateDtoList.add(cateDto);
        });

        //응답 파라미터 설정
        mainRes.setNickName(userInfoDto.get().getNickName());
        mainRes.setLuckCnctn(ydPureCnctn);
        mainRes.setPureCnctn(pureCnctn);
        mainRes.setTodayVersYear(versYear);
        mainRes.setPureCntnt(pureInfoEntity.get().getPureCntnt());
        mainRes.setCharactorFlag(pureInfoEntity.get().getCharactorFlag());
        mainRes.setRecommandCateList(recommandCateDtoList);
        mainRes.setCateDtoList(cateDtoList);


        return mainRes;
    }

}
