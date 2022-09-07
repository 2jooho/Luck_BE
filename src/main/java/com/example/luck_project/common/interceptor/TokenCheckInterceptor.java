//package com.example.luck_project.common.interceptor;
//
//
//import com.example.luck_project.common.exception.ApiException;
//import com.example.luck_project.common.util.ApiAuthEncryptUtils;
//import com.example.luck_project.common.util.ResultCode;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.DateUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class TokenCheckInterceptor implements HandlerInterceptor {
//    final Logger logger = LoggerFactory.getLogger("INFO");
//
////    private HpAuthMapper authMapper;
//
//
//    public TokenCheckInterceptor() {}
////    public TokenCheckInterceptor(HpAuthMapper authMapper) {
////        this.authMapper = authMapper;
////    }
//
//
//    /*
//     * @ApiConfig.java에 설정
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        SimpleDateFormat REQ_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
//
//        String sccServiceId = StringUtils.defaultString(request.getHeader("sccServiceId"));
//        String sccCheck 	= StringUtils.defaultString(request.getHeader("sccCheck"));
//
//        logger.info("{authCheck} -> sccServiceId:{}, sccCheck:{}", sccServiceId, sccCheck);
//
//
//        try {
//            if (StringUtils.isBlank(sccServiceId)) {
//                throw new ApiException(ResultCode.INVALID_TOKEN, "서비스 그룹코드(sccServiceId) 빈값");
//            }
//
//            if (StringUtils.isBlank(sccCheck)) {
//                throw new ApiException(ResultCode.INVALID_TOKEN, "서비스 요청체크값(sccCheck) 빈값");
//            }
//
//            if (sccServiceId.length() != 7) {
//                throw new ApiException(ResultCode.INVALID_TOKEN, "서비스 그룹코드(sccServiceId) length 오류");
//            }
//
//            if (sccCheck.length() > 2048) {
//                throw new ApiException(ResultCode.INVALID_TOKEN, "서비스 요청체크값(sccCheck) length 오류");
//            }
//
//            // 제휴사 인증키 조회
////            String secretKey = authMapper.getSecretKey(sccServiceId);
////            if (StringUtils.isBlank(secretKey)) {
////                throw new ApiException(ResultCode.INVALID_TOKEN, "제휴사의 Secret Key가 존재하지 않음");
////            }
//
//
//            String plainCheckKey = "";
//            try {
////                ApiAuthEncryptUtils encryptUtils = new ApiAuthEncryptUtils(secretKey);
////                plainCheckKey = encryptUtils.decrypt(sccCheck);
//            } catch (IllegalStateException e) {
//                throw new ApiException(ResultCode.INVALID_TOKEN, "토큰키 복호화 실패->{}" + e.getMessage());
//            }
//
//            String requestTime    = plainCheckKey.substring(0, 14);
//            String affiliateCode2 = plainCheckKey.substring(14);
//
//            if(StringUtils.isBlank(requestTime)) {
//                throw new ApiException(ResultCode.INVALID_TOKEN, "토큰키의 날짜정보가 존재하지 않음");
//            }
//
//            if(!StringUtils.isNumeric(requestTime)) {
//                throw new ApiException(ResultCode.INVALID_TOKEN, "토큰키의 날짜정보 형식 오류");
//            }
//
//            Date expiredTime = DateUtils.addMinutes(REQ_DATE_FORMAT.parse(requestTime), 10);
//            Date currentTime = new Date();
//
//            if (currentTime.compareTo(expiredTime) > 0) {
//                throw new ApiException(ResultCode.NO_SIRN_TOKEN, "토큰키 만료");
//            }
//
//            if (!sccServiceId.equals(affiliateCode2)) {
//                throw new ApiException(ResultCode.INVALID_TOKEN, "토큰키의 서비스 그룹코드와 요청의 서비스 그룹코드 불일치");
//            }
//        } catch(ApiException apiEx) {
//            logger.info("[{}]인증에러->{}", sccServiceId, apiEx.getErrMsg());
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//            throw apiEx;
//        } catch(Exception ex) {
//            logger.error("[{}]인증에러->{}", sccServiceId, ex.toString());
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//
//            throw ex;
//        }
//
//        return HandlerInterceptor.super.preHandle(request, response, handler);
//    }
//
//}