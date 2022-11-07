package com.example.luck_project.controller;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.dto.request.LoginReq;
import com.example.luck_project.dto.request.OtpReq;
import com.example.luck_project.dto.response.LoginRes;
import com.example.luck_project.dto.response.OtpRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import com.example.luck_project.service.LoginService;
import com.example.luck_project.service.OtpService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/luck")
public class OtpController extends ApiSupport {

    @Autowired
    private OtpService otpService;

    @PostMapping("/common/otp/send.do")
    public ResponseEntity<OtpRes> luckLogin(@Validated @RequestBody OtpReq otpReq){
        Map<String, String> paramMap = new HashMap<>();
        String osType = otpReq.getOsType();

        if(! (StringUtils.equals("1", otpReq.getOsType()) || StringUtils.equals("2", otpReq.getOsType()) || StringUtils.equals("9", otpReq.getOsType()))){
            throw new CustomException(ErrorCode.VALIDATION_FAIL);
        }

        logger.info("[{}] OTP 컨트롤러", osType);

        paramMap.put("osType", osType);
        OtpRes otpRes = otpService.sendOtp(paramMap);

        return new ResponseEntity<>(otpRes, HttpStatus.OK);
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