package com.example.luck_project.exception;

import com.example.luck_project.common.config.ApiSupport;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.example.luck_project.exception.ErrorHttpStatusMapper.mapToStatus;


@RestControllerAdvice
public class GlobalExceptionHandler extends ApiSupport {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException ce){
        final ErrorCode errorCode = ce.getErrorCode();
        logger.error("errorStatus: {} / errorMessage: {}", errorCode.getStatus(), errorCode.getMessage());
        String encodeMessage = "";
        HttpHeaders headers = new HttpHeaders();

        try {
            encodeMessage = URLEncoder.encode(errorCode.getMessage(), "UTF-8");
        } catch(UnsupportedEncodingException unEx) {
            encodeMessage = "";
        } finally {
            headers.set("resultCode", String.valueOf(errorCode.getStatus()));
            headers.set("resultMessage", encodeMessage);
        }

        return new ResponseEntity<>(null ,headers, mapToStatus(errorCode));
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity handleServerException(Exception ex) {
        logger.error("handleException: {}", ex.getMessage());
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(final RuntimeException re) {
        logger.error("handleRuntimeException : {}", re.getMessage());
        return re.getMessage();
    }

    @Data
    public static class ErrorResponse {
        private final int errorStatus;
        private final String errorMessage;
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
