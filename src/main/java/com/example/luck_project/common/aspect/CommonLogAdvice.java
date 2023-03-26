package com.example.luck_project.common.aspect;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.util.ParamUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;

@Aspect
@Component
public class CommonLogAdvice extends ApiSupport {

    /** Controller Log */
    @Around("execution(* co.kr.starbucks..*Controller.*(..))")
    public Object controllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletRequestWrapper reqWrapper = new HttpServletRequestWrapper(request);

        String className  = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String remoteIp   = ParamUtils.getIp(request);

        String sccServiceId = reqWrapper.getHeader("sccServiceId");
        String requestUri   = reqWrapper.getRequestURI();

        StopWatch watch = new StopWatch();

        try {
            logger.info("Connected: ["+this.strCRLF(remoteIp)+"]["+this.strCRLF(sccServiceId)+"]["+this.strCRLF(requestUri)+"]");

            watch.start();

            logger.info("["+this.strCRLF(className)+":"+this.strCRLF(methodName)+"] ########## START ##########");

            obj = joinPoint.proceed();
        } finally {
            watch.stop();

            logger.info("["+this.strCRLF(className)+":"+this.strCRLF(methodName)+"] ########## END ##########");

            logger.info("Completed: ["+this.strCRLF(remoteIp)+"]["+this.strCRLF(sccServiceId)+"]["+this.strCRLF(requestUri)+"]:("+this.strCRLF(watch.getTime())+")");
        }

        return obj;
    }


    /** Service Log */
    @Around("execution(* co.kr.starbucks..*Service.*(..))")
    public Object serviceAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;

        String className  = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        try {
            logger.info("["+this.strCRLF(className)+"] ----- START ("+this.strCRLF(methodName)+") -----");

            obj = joinPoint.proceed();
        } finally {
            logger.info("["+this.strCRLF(className)+"] ----- END ("+this.strCRLF(methodName)+") -----");
        }

        return obj;
    }

    /** Result Log */
    @AfterReturning(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping))", returning = "jsonView")
    public void responseMessage(JoinPoint joinPoint, Object jsonView) {
        HttpServletRequest request   = null;
        HttpServletResponse response = null;

        StringBuffer log = new StringBuffer(1024);
        String servletPath = "";

        // 결과코드/메시지
        String resultCode 	 = "";
        String resultMessage = "";

        // 요청 파라미터 값
        String params = "";

        try {
            //request, response 획득
            request  = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
            response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();

            //결과 메시지 획득
            if(response != null){
                resultCode 	  = response.getHeader("resultCode");
                resultMessage = StringUtils.defaultString(response.getHeader("resultMessage"), "");
                if(!resultMessage.isBlank()) {
                    URLDecoder.decode(resultMessage, "UTF-8");
                }
            }

            servletPath  = StringUtils.defaultIfEmpty(request.getServletPath(), "");
            Object paramsObj = request.getAttribute("params");

            // 요청 파라미터 획득
            if (paramsObj != null) {
                ObjectMapper mapper = new ObjectMapper();
                params = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paramsObj);
            }

            String requestStr = "[" + servletPath + "](" + ParamUtils.getIp(request) + ")";
            log.append(requestStr);
            log.append("\n[HEADER] ").append(this.writeHeaderLog(request));
            log.append("\n[PARAMS] ").append(params);

            log.append("\n[RESULT_HEADER] ").append(resultCode).append(':').append(resultMessage);

        } catch(UnsupportedEncodingException uee) {
            logger.error("[" + this.strCRLF(servletPath) + "] UnsupportedEncodingException -> " + this.strCRLF(uee.getMessage()));
        } catch(JsonProcessingException jpe) {
            logger.error("[" + this.strCRLF(servletPath) + "] JsonProcessingException -> " + this.strCRLF(jpe.getMessage()));
        } finally {
            logger.error(this.strCRLF(log.toString()));
        }
    }

    /**
     * Header 정보 조회
     * @param request
     */
    public String writeHeaderLog(HttpServletRequest request){
        HttpServletRequestWrapper reqWrapper = new HttpServletRequestWrapper(request);

        Enumeration<String> headerNames = reqWrapper.getHeaderNames();
        ArrayList<String> headerList    = new ArrayList<String>();
        while (headerNames.hasMoreElements()) {
            String name  = (String)headerNames.nextElement();
            String value = reqWrapper.getHeader(name);
            headerList.add(name + ":" + value);
        }

        return StringUtils.join(headerList, ",");
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