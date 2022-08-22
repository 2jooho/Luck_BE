package com.luckC.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class mainService {

    public final static String LOGIN_SESSION_KEY = "USER_ID";

    public MainRes main(MainReq mainReq, HttpSession session) {
        final String userId = String.valueOf(session.getAttribute(LOGIN_SESSION_KEY));
        final String prefix = "[" + strCRLF(userId) + "]";

        if (userId != null) {
            log.info("{}세션 정보가 존재하지 않습니다.", prefix);
            throw new handleCustomException(ErrorCode.BAD_REQUEST);
        }
        final Optional<todayLuckRepository> todayLuckRepository

        return MainRes;
    }

    /**
     * CRLF 개행제거
     */
    public String strCRLF(Object obj) {
        String retStr = null;

        if (obj != null) {
            if (obj instanceof Exception) {
                retStr = ((Exception) obj).getStackTrace().toString().replaceAll("\r\n", "");
            } else {
                retStr = obj.toString().replaceAll("\r\n", "");
            }
        }

        return retStr;
    }

}
