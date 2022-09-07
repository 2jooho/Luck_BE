package com.example.luck_project.common.aspect;

import com.example.luck_project.common.util.ParamUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j
public class CommonLogAdvice {

    /** Controller Log */
    @Around("execution(* com.example.luck_project..*Controller.*(..))")
    public Object controllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String className  = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String remoteIp   = ParamUtils.getIp(request);

        StopWatch watch = new StopWatch();

        try {
            log.info("Connected: ["+remoteIp+"]["+request.getHeader("sccServiceId")+"]["+request.getRequestURI()+"]");

            watch.start();

            log.info("["+className+":"+methodName+"] ########## START ##########");

            obj = joinPoint.proceed();
        } finally {
            watch.stop();

            log.info("["+className+":"+methodName+"] ########## END ##########");

//            log.info("Completed: ["+remoteIp+"]["+request.getHeader("sccServiceId")+"]["+request.getRequestURI()+"]:("+watch.getTotalTimeSeconds()+")");
        }

        return obj;
    }


    /** Service Log */
    @Around("execution(* com.example.luck_project..*Service.*(..))")
    public Object serviceAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;

        String className  = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        try {
            log.info("["+className+"] ----- START ("+methodName+") -----");

            obj = joinPoint.proceed();
        } finally {
            log.info("["+className+"] ----- END ("+methodName+") -----");
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
                resultMessage = URLDecoder.decode(response.getHeader("resultMessage"), "UTF-8");
            }

            servletPath  = StringUtils.defaultIfEmpty(request.getServletPath(), "");
            Object paramsObj = request.getAttribute("params");

            // 요청 파라미터 획득
            if (paramsObj != null) {
                ObjectMapper mapper = new ObjectMapper();
                params = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paramsObj);
            }

            log.append("[").append(servletPath).append("](").append(ParamUtils.getIp(request)).append(")").append("\n");
            log.append("[HEADER] ").append(this.writeHeaderLog(request)).append("\n");
            log.append("[PARAMS] ").append(params).append("\n");

            log.append("[RESULT_HEADER] ").append(resultCode).append(" : ").append(resultMessage).append("\n");
        } catch(Exception ex) {
            log.append("[" + servletPath + "] Exception -> " + ex.getMessage());
        } finally {
            log.append(log.toString());
        }
    }

    /**
     * Header 정보 조회
     * @param request
     */
    public String writeHeaderLog(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        ArrayList<String> headerList    = new ArrayList<String>();
        while (headerNames.hasMoreElements()) {
            String name  = (String)headerNames.nextElement();
            String value = request.getHeader(name);
            headerList.add(name + ":" + value);
        }

        return StringUtils.join(headerList, ",");
    }

}