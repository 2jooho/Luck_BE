package com.example.luck_project.controller;

import com.example.luck_project.dto.request.PureLuckMainReq;
import com.example.luck_project.dto.response.PureLuckMainRes;
import com.example.luck_project.service.PureLuckService;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class PureLuckMainController {

    @Autowired
    private PureLuckService pureLuckService;

    /**
     * 비장술 정보 조회
     * @param pureLuckMainReq
     * @return
     */
    @PostMapping("/pureLuckMain.do")
    public ResponseEntity<PureLuckMainRes> pureLuckMain(@Validated @RequestBody PureLuckMainReq pureLuckMainReq){
        Map<String, Object> reqMap = new HashMap<>();

        String userId = pureLuckMainReq.getUserId().toUpperCase();
        String pureCnctn = pureLuckMainReq.getPureCnctn();
        String cateDetailCode = pureLuckMainReq.getCateDetailCode().toUpperCase();
        String todayVersYear = pureLuckMainReq.getTodayVersYear();

        reqMap.put("userId", userId);
        reqMap.put("pureCnctn", pureCnctn);
        reqMap.put("cateDetailCode", cateDetailCode);
        reqMap.put("todayVersYear", todayVersYear);


        log.info("[{}][{}][{}] 비장술 정보 조회", strCRLF(userId), strCRLF(pureCnctn), strCRLF(cateDetailCode));

        PureLuckMainRes pureLuckMainRes = pureLuckService.pureLuckMain(reqMap);

        return new ResponseEntity<>(pureLuckMainRes, HttpStatus.OK);

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
