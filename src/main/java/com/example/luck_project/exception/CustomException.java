package com.example.luck_project.exception;

import com.example.luck_project.constants.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.example.luck_project.constants.ResponseCode.SERVER_ERROR;



public class CustomException extends RuntimeException {
//    @Getter
//    private final ErrorCode errorCode;
    @Getter private final String resultCode;
    @Getter private final String resultMessage;
    @Getter private final HttpStatus httpStatus;
    @Getter private final transient Object body;

     public CustomException() {
        this(SERVER_ERROR);
    }
    public CustomException(ResponseCode responseCode) {
        super("[" + responseCode.getResponseCode() + "]"+responseCode.getMessage());
        this.resultCode = SERVER_ERROR.getResponseCode();
        this.resultMessage = responseCode.getMessage();
        this.body = null;
        this.httpStatus = responseCode.getHttpStatus();
    }

    public CustomException(ResponseCode responseCode, String resultMessage) {
        super("[" + responseCode.getResponseCode() + "]"+responseCode.getMessage());
        this.resultCode = SERVER_ERROR.getResponseCode();
        this.resultMessage = resultMessage;
        this.body = null;
        this.httpStatus = responseCode.getHttpStatus();
    }
    public CustomException(String resultCode, String resultMessage, Object responseBody) {
        super("[" + resultCode + "] " + resultMessage);
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.body = responseBody;
        this.httpStatus = ResponseCode.getHttpStatusFromResponseCode(resultCode);
    }

}
