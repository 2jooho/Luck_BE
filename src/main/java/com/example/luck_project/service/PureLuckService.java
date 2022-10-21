package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.dto.response.PureLuckMainRes;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PureLuckService extends ApiSupport {

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

        logger.info("[{}][{}][{}] ", userId, pureCnctn, cateDetailCode);
        //사용자 비장술 조합 목록 조회

        //


        //10/20
        //정의 : 해당 카테고리의 최고 조홥이 순위로
        // 만약 금조건/퇴식 1위, 합식/원진록 2위, 해결신/천록 3위...
        // 내가 금조건/퇴식이 있다.
        //
        //1.


//        1. 사용자의 비장술 조합 정보를 가지고 있다. 비장술 조합 정보테이블에는 해당 비장술에 해당하는 띠 정보(일에 해당하는 띠)도 가지고 있다.
//        2. 오늘에 해당하는 띠와 최고 조합에 해당하는 띠를 빼서 일수를 구한다. (몇월 몇일)
//        3. 최고 조합 띠날을 기준으로 시간을 돌려서 찾는다(ex 합식이면 강일진+3 강일진 시작시간 +6시간이 합식 시작시간)
//        4. 오늘 띠를 기준으로 최고 시간대를 찾는다. (ex 합식이면 강일진+3 강일진 시작시간 +6시간이 합식 시작시간) (묘 날이면 묘가05~00 시작이니까 11:00부터 13:00이 최적 시간)




        //응답 파라미터 설정

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
