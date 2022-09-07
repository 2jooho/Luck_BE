package com.example.luck_project.common.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResponseUtility {
    private HttpServletRequest request;
    private HttpServletResponse response;


    /**
     * 응답전문 생성
     * @param request
     * @param response
     */
    public ResponseUtility (HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 응답전문 생성
     * @param request
     * @param response
     * @param obj : 요청 파라미터 출력용
     */
    public ResponseUtility (HttpServletRequest request, HttpServletResponse response, Object obj) {
        this.request = request;
        this.response = response;
        this.request.setAttribute("params", obj);
    }


    /**
     * <pre>
     * Header 추가
     * </pre>
     * @param returnCode
     */
    public void setHeader(String returnCode) {
        this.response.setHeader("resultCode",    returnCode);
        this.response.setHeader("resultMessage", this.getResultMessage(returnCode));
    }

    /**
     * <pre>
     * Header 추가
     * </pre>
     * @param returnCode
     * @param statusCode
     */
    public void setHeaderAndStatus(String returnCode, int statusCode) {
        this.response.setHeader("resultCode",    returnCode);
        this.response.setHeader("resultMessage", this.getResultMessage(returnCode));
        response.setStatus(statusCode);
    }


    /**
     * resultMessage 설정
     * @param returnCode
     * @return String
     */
    private String getResultMessage(String returnCode) {
        String resultMessage = "";

        try {
            resultMessage = URLEncoder.encode(MessageUtil.getMessage(returnCode), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            resultMessage = "";
        }

        return resultMessage;
    }
}