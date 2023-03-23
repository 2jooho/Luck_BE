package com.example.luck_project.exception;

import com.example.luck_project.common.util.ReplaceStringUtil;
import com.example.luck_project.constants.ResponseCode;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.UnexpectedException;
import java.util.Objects;

import static com.example.luck_project.constants.ResponseCode.INVALID_PARAMETER;
import static com.example.luck_project.constants.ResponseCode.SERVER_ERROR;
import static com.example.luck_project.constants.StaticValues.RESULT_CODE;
import static com.example.luck_project.constants.StaticValues.RESULT_MESSAGE;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private HttpHeaders setHeaders(String responseCode, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(RESULT_CODE, responseCode);
        headers.add(RESULT_MESSAGE, message);

        return headers;
    }

//    @ExceptionHandler(ParamCustomException.class)
//    protected ResponseEntity<Map<String, Object>> handleCustomException(final ParamCustomException ce){
//        final ErrorCode errorCode = ce.getErrorCode();
//        log.error("errorStatus: {} / errorMessage: {}", errorCode.getStatus(), errorCode.getMessage());
//        String encodeMessage = "";
//        HttpHeaders headers = new HttpHeaders();
//
//        try {
//            encodeMessage = URLEncoder.encode(errorCode.getMessage(), "UTF-8");
//        } catch(UnsupportedEncodingException unEx) {
//            encodeMessage = "";
//        } finally {
//            headers.set("resultCode", String.valueOf(errorCode.getStatus()));
//            headers.set("resultMessage", encodeMessage);
//        }
//
//        return new ResponseEntity<>(ce.getExceptionMap() ,headers, mapToStatus(errorCode));
//    }

    @ExceptionHandler({JpaSystemException.class, UnexpectedException.class})
    protected ResponseEntity<Object> handle(Exception ex){
        log.info("DB Error", ex);

        ResponseCode resultCode = SERVER_ERROR;
        HttpHeaders headers = setHeaders(resultCode.getResponseCode(), resultCode.getUrlEncodingMessage());

        ApiResponse response = new ApiResponse(resultCode.getResponseCode(), resultCode.getUrlEncodingMessage());

        return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ValidationException.class})
    protected ResponseEntity<Object> handle(ValidationException ve) {
        log.error("ValidationException : ", ve);
        if (ve.getCause() instanceof CustomException) {
            return handleCustomException((CustomException) ve.getCause());
        }

        HttpHeaders headers = setHeaders(INVALID_PARAMETER.getResponseCode(), INVALID_PARAMETER.getUrlEncodingMessage());
        ApiResponse response = new ApiResponse(INVALID_PARAMETER.getResponseCode(), INVALID_PARAMETER.getUrlEncodingMessage());
        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(final CustomException ce){
        String encodeResultMessage = URLEncoder.encode(ce.getMessage(), StandardCharsets.UTF_8);
        HttpHeaders headers = setHeaders(ce.getResultCode(), encodeResultMessage);


        log.error("CustomException Code ={}, Message={}, HttpStatus={} Exception={}",
                ReplaceStringUtil.replaceStringCRLF(ce.getResultCode()),
                ReplaceStringUtil.replaceStringCRLF(ce.getResultMessage()),
                ReplaceStringUtil.replaceStringCRLF(Objects.toString(ce.getHttpStatus())),
                ReplaceStringUtil.replaceStringCRLF(ExceptionUtils.getStackTrace(ce)));

        ApiResponse response = new ApiResponse(ce.getResultCode(), encodeResultMessage, ce.getBody());

        return new ResponseEntity<>(response ,headers, ce.getHttpStatus());
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity handleServerException(Exception ex) {
        ResponseCode resultCode = SERVER_ERROR;
        HttpHeaders headers = setHeaders(resultCode.getResponseCode(), resultCode.getUrlEncodingMessage());

        if(isCritical(ex)){
            log.error("[CRITICAL]", ex);
        } else {
            log.error("Exception", ex);
        }

        ApiResponse response = new ApiResponse(resultCode.getResponseCode(), resultCode.getUrlEncodingMessage());
        return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity handleRuntimeException(RuntimeException re) {
//        log.error("handleRuntimeException : {}", re.getMessage());
//        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Data
    public static class ErrorResponse {
        private final int errorStatus;
        private final String errorMessage;
    }

    private boolean isCritical(Exception ex) {
        return ex.getCause() instanceof ReadTimeoutException;
    }

}
