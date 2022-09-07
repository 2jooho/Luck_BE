//package com.example.luck_project.common.aspect;
//
//import com.example.luck_project.common.exception.ApiException;
//import com.example.luck_project.common.util.MessageUtil;
//import com.example.luck_project.common.util.ResultCode;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//@ControllerAdvice
//@RestController
//@Slf4j
//public class ApiExceptionAdvice {
//
//    // ApiException 처리
//    @ExceptionHandler(ApiException.class)
//    public Object apiAuthException(HttpServletRequest request, HttpServletResponse response, ApiException apiEx){
//        String resultCode = apiEx.getResultCode();
//        String resultMessage = apiEx.getResultMessage();
//        String encodeMessage = "";
//
//        log.info("Api_ERROR : {}", apiEx.getErrMsg());
//
//        try {
//            encodeMessage = URLEncoder.encode(resultMessage, "UTF-8");
//        } catch(UnsupportedEncodingException unEx) {
//            encodeMessage = "";
//        } finally {
//            response.setHeader("resultCode", 	resultCode);
//            response.setHeader("resultMessage", encodeMessage);
//        }
//
//        return null;
//    }
//
//
//    // Exception 처리
//    @ExceptionHandler(Exception.class)
//    public Object exception(HttpServletRequest request, HttpServletResponse response, Exception ex){
//        String resultCode = ResultCode.SERVER_ERROR;
//        String resultMessage = MessageUtil.getMessage(resultCode);
//        String encodeMessage = "";
//
//        log.error("Exception_ERROR : {}", ex.getMessage());
//
//        try {
//            encodeMessage = URLEncoder.encode(resultMessage, "UTF-8");
//        } catch(UnsupportedEncodingException unEx) {
//            encodeMessage = "";
//        } finally {
//            response.setHeader("resultCode",    resultCode);
//            response.setHeader("resultMessage", encodeMessage);
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//
//        return null;
//    }
//
//    // ValidException 처리
//    @ResponseStatus(HttpStatus.CONFLICT)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected Object handleMethodArgumentNotValidException(HttpServletResponse response, MethodArgumentNotValidException e) {
//        String resultCode = ResultCode.INVALID_PARMETER;
//        String resultMessage = MessageUtil.getMessage(resultCode);
//        String encodeMessage = "";
//
//        log.info("ValidException_ERROR : {}", e.getMessage());
//
//        try {
//            encodeMessage = URLEncoder.encode(resultMessage, "UTF-8");
//        } catch(UnsupportedEncodingException unEx) {
//            encodeMessage = "";
//        } finally {
//            response.setHeader("resultCode",    resultCode);
//            response.setHeader("resultMessage", encodeMessage);
//        }
//
//        return null;
//    }
//}