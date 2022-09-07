package com.example.luck_project.common.exception;

import com.example.luck_project.common.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings({"serial"})
public class ApiException extends RuntimeException {
    /** 오류코드 */
    private String resultCode;

    /** 오류메시지 */
    private String resultMessage;

    /** 사용자 정의 메시지 */
    private String userMessage;


    /**
     * resultMessage는 resultCode에 맞게 자동으로 설정
     * @param resultCode : 오류코드
     */
    public ApiException (String resultCode) {
        super(MessageUtil.getMessage(resultCode));

        this.resultCode = resultCode;
        this.resultMessage = MessageUtil.getMessage(resultCode);
    }

    /**
     * resultMessage는 resultCode에 맞게 자동으로 설정 <br>
     * @param resultCode : 오류코드
     * @param userMessage : 사용자 정의 에러 메시지 (로그용, 헤더에 설정되지 않음)
     */
    public ApiException (String resultCode, String userMessage) {
        super(userMessage);

        this.resultCode = resultCode;
        this.resultMessage = MessageUtil.getMessage(resultCode);
        this.userMessage = userMessage;
    }


    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * @return resultCode, resultMessage, userMessage를 한번에 리턴
     */
    public String getErrMsg() {
        String errLog = resultCode+"|"+resultMessage;

        if (StringUtils.isNotBlank(userMessage)) {
            errLog += "||"+userMessage;
        }

        return errLog;
    }
}
