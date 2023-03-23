package com.example.luck_project.controller;

import com.example.luck_project.common.util.ReplaceStringUtil;
import com.example.luck_project.controller.constants.BaseController;
import com.example.luck_project.dto.request.PureLuckMainReq;
import com.example.luck_project.dto.response.PureLuckMainRes;
import com.example.luck_project.service.PureLuckService;
import lombok.extern.slf4j.Slf4j;
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

import static com.example.luck_project.controller.constants.ApiUrl.*;

@RestController
@RequestMapping(BASE_URL)
@Slf4j
public class PureLuckMainController extends BaseController {

    @Autowired
    private PureLuckService pureLuckService;

    /**
     * 비장술 정보 조회
     * @param pureLuckMainReq
     * @return
     */
    @PostMapping(PURE_LUCK_MAIN_URL)
    public ResponseEntity<PureLuckMainRes> pureLuckMain(@Validated @RequestBody PureLuckMainReq pureLuckMainReq){
        Map<String, Object> reqMap = new HashMap<>();

        String userId = pureLuckMainReq.getUserId().toUpperCase();
        String pureCnctn = pureLuckMainReq.getPureCnctn();
        String cateCode = pureLuckMainReq.getCateCode().toUpperCase();
        String cateDetailCode = pureLuckMainReq.getCateDetailCode().toUpperCase();
        String todayVersYear = pureLuckMainReq.getTodayVersYear();

        reqMap.put("userId", userId);
        reqMap.put("pureCnctn", pureCnctn);
        reqMap.put("cateCode", cateCode);
        reqMap.put("cateDetailCode", cateDetailCode);
        reqMap.put("todayVersYear", todayVersYear);


        log.info("[{}][{}][{}] 비장술 정보 조회", ReplaceStringUtil.replaceStringCRLF(userId), ReplaceStringUtil.replaceStringCRLF(pureCnctn), ReplaceStringUtil.replaceStringCRLF(cateDetailCode));

        PureLuckMainRes pureLuckMainRes = pureLuckService.pureLuckMain(reqMap);

        return new ResponseEntity<>(pureLuckMainRes, getSuccessHeaders(), HttpStatus.OK);

    }

}
