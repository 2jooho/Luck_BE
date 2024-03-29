package com.example.luck_project.common.interceptor;

import com.example.luck_project.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.luck_project.constants.ResponseCode.BAD_REQUEST;


@Component
@Slf4j
public class ApiAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //======================================================================================
        // 1. Request Method 체크 => 인터페이스는 POST, GET, PATCH, DELETE 메소드만 사용
        //======================================================================================
        if (isRequiredMethod(request)) {
            throw new CustomException(BAD_REQUEST);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    private boolean isRequiredMethod(HttpServletRequest request) {
        return !"POST".equalsIgnoreCase(request.getMethod()) && !"PATCH".equalsIgnoreCase(request.getMethod())
                && !"GET".equalsIgnoreCase(request.getMethod()) && !"DELETE".equalsIgnoreCase(request.getMethod());
    }

}