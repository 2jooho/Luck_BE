package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.util.DataCode;
import com.example.luck_project.domain.CateDetailPureEntity;
import com.example.luck_project.domain.UserPureCombinationEntity;
import com.example.luck_project.dto.BestDayAndTimeDto;
import com.example.luck_project.dto.TodayBestTimeDto;
import com.example.luck_project.dto.response.PureLuckMainRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.CateDetailPureRepository;
import com.example.luck_project.repository.UserPureCombRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.luck_project.exception.ErrorCode.CATE_DETL_PURE_NOT_FOUND;

@Service
public class PureLuckService extends ApiSupport {

    @Autowired
    CateDetailPureRepository cateDetailPureRepository;
    @Autowired
    UserPureCombRepository userPureCombRepository;


    /**
     * 비장술 정보 조회
     * @param reqMap
     * @return
     */
    public PureLuckMainRes pureLuckMain(Map<String, Object> reqMap){
        PureLuckMainRes pureLuckMainRes = new PureLuckMainRes();
        String userId = String.valueOf(reqMap.get("userId"));
        String pureCnctn = String.valueOf(reqMap.get("pureCnctn"));
        String cateDetailCode = String.valueOf(reqMap.get("cateDetailCode"));
        String todayVersYear = String.valueOf(reqMap.get("todayVersYear"));

        logger.info("[{}][{}][{}] 상세 카테고리 비장술 목록 조회", userId, pureCnctn, cateDetailCode);
        //상세 카테고리 비장술 목록 조회
        Optional<List<CateDetailPureEntity>> cateDetailPureEntityList = cateDetailPureRepository.findByCateDetlCodeOrderByPureOrder(cateDetailCode);
        cateDetailPureEntityList.orElseThrow(() -> new CustomException(CATE_DETL_PURE_NOT_FOUND));

        List<String> pureTypeList = new ArrayList<>();
        cateDetailPureEntityList.get().stream().forEach(cateDetailPureEntity -> pureTypeList.add(cateDetailPureEntity.getPureType()));

        //비장술 조합 정보 조회
        Optional<UserPureCombinationEntity> userPureCombinationEntity = Optional.of(new UserPureCombinationEntity());
        for(String pureType : pureTypeList){
            userPureCombinationEntity = userPureCombRepository.findTop1ByPureYearAndLuckCnctnAndPureDayIn(pureType, pureCnctn, pureTypeList);
            if (userPureCombinationEntity.isPresent()){
                break;
            }
        }
        if(!userPureCombinationEntity.isPresent()){
            userPureCombinationEntity = userPureCombRepository.findTop1ByPureYearInOrPureDayInAndLuckCnctn(pureTypeList, pureTypeList, pureCnctn);
            userPureCombinationEntity.orElseThrow(() -> new CustomException(CATE_DETL_PURE_NOT_FOUND));
        }


        logger.info("날짜 정보 찾기");
        Optional<Integer> todayCodeNum = Optional.of(DataCode.getCodeNum(DataCode.VERS_YEAR_NAME_ARR, todayVersYear));
        Optional<Integer> userCodeNum = Optional.of(DataCode.getCodeNum(DataCode.VERS_YEAR_NAME_ARR, userPureCombinationEntity.get().getVersYear()));

        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String todayDate = today.format(formatter);
        String pureDate = String.valueOf(Integer.valueOf(todayDate) + (((12-todayCodeNum.get())+userCodeNum.get())));
        logger.info("test : {}/{}/{}/{}", todayDate, todayCodeNum.get(), userCodeNum.get(), pureDate);

        logger.info("최고 시간 구하기(랜덤)");
        //오늘 띠 숫자 + 랜덤(강천일합금)숫자
        Collections.shuffle(pureTypeList);
        int pureTimeNum = userCodeNum.get() + DataCode.getCodeNum(DataCode.PURE_LUCK_NAME_ARR, pureTypeList.get(0));
        logger.info("확인스 : {}/ {}/ {}/{}", pureTimeNum, todayCodeNum.get(),DataCode.getCodeNum(DataCode.PURE_LUCK_NAME_ARR, pureTypeList.get(0)), pureTypeList.get(0));
        String pureTime = "";
        if(pureTimeNum > 12){
            pureTime = DataCode.VERS_YEAR_TIME_ARR[pureTimeNum-14];
        }else{
            pureTime = DataCode.VERS_YEAR_TIME_ARR[pureTimeNum-2];
        }

        logger.info("확인스 : {}/{}", pureTime , pureTimeNum);
        List<BestDayAndTimeDto> bestDayAndTimeDtoList = new ArrayList<>();
        BestDayAndTimeDto bestDayAndTimeDto = new BestDayAndTimeDto();
        bestDayAndTimeDto.setBestDate(pureDate);
        bestDayAndTimeDto.setBestTime(pureTime);
        bestDayAndTimeDto.setVersYear(DataCode.VERS_YEAR_NAME_ARR[userCodeNum.get()-1]);
        bestDayAndTimeDtoList.add(bestDayAndTimeDto);


        logger.info("오늘의 최고 시간 조회");
        int firstCodeNum= DataCode.getCodeNum(DataCode.PURE_LUCK_NAME_ARR, cateDetailPureEntityList.get().get(0).getPureType());
        int secondCodeNum= DataCode.getCodeNum(DataCode.PURE_LUCK_NAME_ARR, cateDetailPureEntityList.get().get(1).getPureType());

        int best1Time = todayCodeNum.get() + firstCodeNum;
        logger.info("오늘의 최고 시간 확인용 {}/{}/{}", todayCodeNum.get(), firstCodeNum, best1Time);
        int best2Time = todayCodeNum.get() + secondCodeNum;
        logger.info("오늘의 최고 시간 확인용2 {}/{}/{}", todayCodeNum.get(), secondCodeNum, best2Time);

        List<TodayBestTimeDto> todayBestTimeDtoList = new ArrayList<>();

        if(best1Time > 12){
            TodayBestTimeDto todayBestTimeDto = new TodayBestTimeDto();
            best1Time = best1Time-12;
            todayBestTimeDto.setBestTime(DataCode.VERS_YEAR_TIME_ARR[best1Time-1]);
            todayBestTimeDto.setTimeVersYear(DataCode.VERS_YEAR_NAME_ARR[best1Time-1]);
            todayBestTimeDtoList.add(todayBestTimeDto);
        }else{
            TodayBestTimeDto todayBestTimeDto = new TodayBestTimeDto();
            best1Time = best1Time-12;
            todayBestTimeDto.setBestTime(DataCode.VERS_YEAR_TIME_ARR[best1Time-1]);
            todayBestTimeDto.setTimeVersYear(DataCode.VERS_YEAR_NAME_ARR[best1Time-1]);
            todayBestTimeDtoList.add(todayBestTimeDto);
        }

        if(best2Time > 12){
            TodayBestTimeDto todayBestTimeDto = new TodayBestTimeDto();
            best2Time = best2Time-12;
            todayBestTimeDto.setBestTime(DataCode.VERS_YEAR_TIME_ARR[best2Time-1]);
            todayBestTimeDto.setTimeVersYear(DataCode.VERS_YEAR_NAME_ARR[best2Time-1]);
            todayBestTimeDtoList.add(todayBestTimeDto);
        }else{
            TodayBestTimeDto todayBestTimeDto = new TodayBestTimeDto();
            best2Time = best2Time-12;
            todayBestTimeDto.setBestTime(DataCode.VERS_YEAR_TIME_ARR[best2Time-1]);
            todayBestTimeDto.setTimeVersYear(DataCode.VERS_YEAR_NAME_ARR[best2Time-1]);
            todayBestTimeDtoList.add(todayBestTimeDto);
        }

        //메시지 정의




//        1. 사용자의 비장술 조합 정보를 가지고 있다. 비장술 조합 정보테이블에는 해당 비장술에 해당하는 띠 정보(일에 해당하는 띠)도 가지고 있다.
//        2. 오늘에 해당하는 띠와 최고 조합에 해당하는 띠를 빼서 일수를 구한다. (몇월 몇일)
//        3. 최고 조합 띠날을 기준으로 시간을 돌려서 찾는다(ex 합식이면 강일진+3 강일진 시작시간 +6시간이 합식 시작시간)
//        4. 오늘 띠를 기준으로 최고 시간대를 찾는다. (ex 합식이면 강일진+3 강일진 시작시간 +6시간이 합식 시작시간) (묘 날이면 묘가05~00 시작이니까 11:00부터 13:00이 최적 시간)


        //사용완료 +1
//        userPaymentEntity.updateUseCmplnCnt();

        //응답 파라미터 설정
        pureLuckMainRes.setTodayVersYear(todayVersYear);
        pureLuckMainRes.setBestDayAndTimeList(bestDayAndTimeDtoList);
        pureLuckMainRes.setTodayBestTimeList(todayBestTimeDtoList);

        return pureLuckMainRes;
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
