package com.example.luck_project.controller;

import com.example.luck_project.controller.constants.BaseController;
import com.example.luck_project.dto.request.SendSMS;
import com.example.luck_project.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Random;

@RestController
@RequestMapping("/luck")
@Slf4j
public class OtpController extends BaseController {

    @Autowired
    private OtpService otpService;

//    @PostMapping("/common/otp/send.do")
//    public ResponseEntity<OtpRes> luckLogin(@Validated @RequestBody OtpReq otpReq){
//        Map<String, String> paramMap = new HashMap<>();
//        String osType = otpReq.getOsType();
//
//        if(! (StringUtils.equals("1", otpReq.getOsType()) || StringUtils.equals("2", otpReq.getOsType()) || StringUtils.equals("9", otpReq.getOsType()))){
//            throw new CustomException(ResponseCode.VALIDATION_FAIL);
//        }
//
//        log.info("[{}] OTP 컨트롤러", osType);
//
//        paramMap.put("osType", osType);
//        OtpRes otpRes = otpService.sendOtp(paramMap);
//
//        return new ResponseEntity<>(otpRes, HttpStatus.OK);
//    }

//    @PostMapping("/check/sendSMS")
//    public ResponseEntity<Object> sendSMS(@Valid SendSMS sendSMS) {
//        otpService.certifiedPhoneNumber(sendSMS);
//
//        return new ResponseEntity<>(getSuccessHeaders(), HttpStatus.OK);
//    }




}