package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.util.PropertyUtil;
import com.example.luck_project.dto.request.LoginReq;
import com.example.luck_project.dto.response.LoginRes;
import com.example.luck_project.dto.response.OtpRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class OtpService extends ApiSupport {

    @Autowired
    private PropertyUtil propertyUtil;


    /**
     * SMS로 OTP를 발송한다.
     * @param paramMap
     * @return
     */
    @Transactional
    public OtpRes sendOtp(Map<String, String> paramMap) {
        OtpRes otpRes = new OtpRes();
        String userId = paramMap.get("userId");
        String osType = paramMap.get("osType");

        // 패스워드 SHA256 암호화
        logger.info("[{}] 고객 정보 및 휴대폰 번호 등록여부 확인", userId);

        // OTP인증 연속 5회 실패 시 당일 OTP 서비스 이용제한
        logger.info("[{}] OTP 서비스 사용 제한 여부 조회", userId);

        // 마지막 요청시각으로부터 1분내 발송제한

        logger.info("[{}] OTP 발송 ID 생성", userId);
        //OTP발행일시, 만료일시, 실패횟수, 상태코드 등 설정

        logger.info("[{}] OTP 발송처리", userId);
        //발송 메시지 조회 및 설정

        return otpRes;
    }

}
