package com.example.luck_project.service;

import com.example.luck_project.common.util.PropertyUtil;
import com.example.luck_project.common.util.RedisUtil;
import com.example.luck_project.dto.request.ConfirmsSMS;
import com.example.luck_project.dto.request.SendSMS;
import com.example.luck_project.dto.response.OtpRes;
import com.example.luck_project.exception.CustomException;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.luck_project.constants.ResponseCode.PHONE_AUTH_NUMBER_FAIL;
import static com.example.luck_project.constants.ResponseCode.RE_TOKEN_RESPONSE;


@Service
@Slf4j
@RequiredArgsConstructor
public class OtpService {
    @Value("${coolsms.api.key}")
    private String api_key;
    @Value("${coolsms.api.secret}")
    private String api_secret;

    private RedisUtil redisUtil;

    private final long LIMIT_TIME = 3*60L;

    private final String PREFIX = "sms:";
    final DefaultMessageService messageService;

    public OtpService() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("NCSUDHLDODKWEAQM", "F7GFQKOSGB0IFLDYTHXJQLHM1LIANQUJ", "https://api.coolsms.co.kr");
    }

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
        log.info("[{}] 고객 정보 및 휴대폰 번호 등록여부 확인", userId);

        // OTP인증 연속 5회 실패 시 당일 OTP 서비스 이용제한
        log.info("[{}] OTP 서비스 사용 제한 여부 조회", userId);

        // 마지막 요청시각으로부터 1분내 발송제한

        log.info("[{}] OTP 발송 ID 생성", userId);
        //OTP발행일시, 만료일시, 실패횟수, 상태코드 등 설정

        log.info("[{}] OTP 발송처리", userId);
        //발송 메시지 조회 및 설정

        return otpRes;
    }

    /**
     * sms 전송
     * @param sendSMS
     * @return
     */
    public void certifiedPhoneNumber(SendSMS sendSMS){
        String phoneNumber = sendSMS.getPhoneNm();
        String authNm = "";
        Random rand  = new Random();
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            authNm+=ran;
        }
        log.info("authNm: {}",authNm);

        Message coolsms = new Message();
        // 4 params(to, from, type, text) are mandatory. must be filled
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("to", phoneNumber);    // 수신전화번호
//        params.put("from", "020000000");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
//        params.put("type", "SMS");
//        params.put("text", "핫띵크 휴대폰인증 테스트 메시지 : 인증번호는" + "["+authNm+"]" + "입니다.");
//        params.put("app_version", "test app 1.0"); // application name and version
        coolsms.setFrom("01047886112");
        coolsms.setTo(phoneNumber);
        coolsms.setText("휴대폰인증 테스트 메시지 : ");

//        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(coolsms));
//        System.out.println(response);
        //redis에  3분 동안 넣기
        Boolean isRedisSet = redisUtil.setExpireValue(PREFIX + phoneNumber, authNm, LIMIT_TIME);
        log.info("isRedisSet:{}", isRedisSet);
    }

    /**
     * 인증번호 확인
     * @param confirmsSMS
     */
    public void verifySms(ConfirmsSMS confirmsSMS) {
        String authNm = confirmsSMS.getAuthNm();
        String phoneNumber = confirmsSMS.getPhoneNm();
        if(!StringUtils.equals(String.valueOf(redisUtil.getValue(PREFIX + phoneNumber)), authNm)){
            log.info("isRedisGet 실패");
            throw new CustomException(PHONE_AUTH_NUMBER_FAIL);
        }
    }


}
