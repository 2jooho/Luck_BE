package com.example.luck_project.common.util;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ParamUtils {

    /**
     * 접속 IP 획득
     * @param request
     * @return String
     */
    public static String getIp(HttpServletRequest request) {
        String ip = StringUtils.defaultString(request.getHeader("X-Forwarded-For"));

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = StringUtils.defaultString(request.getHeader("Proxy-Client-IP"));
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = StringUtils.defaultString(request.getHeader("WL-Proxy-Client-IP"));  // 웹로직
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = StringUtils.defaultString(request.getHeader("HTTP_CLIENT_IP"));
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = StringUtils.defaultString(request.getHeader("HTTP_X_FORWARDED_FOR"));
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = StringUtils.defaultString(request.getRemoteAddr());
        }

        if (StringUtils.defaultString(ip).length() > 1024) {
            ip = "";
        }

        return ip;
    }
}