package com.example.luck_project.controller;

import com.example.luck_project.dto.request.CateListReq;
import com.example.luck_project.dto.response.CateListRes;
import com.example.luck_project.service.CateService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/luck")
@Log4j2
public class LuckCateController {

    @Autowired
    private CateService cateService;

    /**
     * 카테고리 목록 조회
     * @param cateListReq
     * @return
     */
    @PostMapping("/cateDetailList.do")
    public ResponseEntity<CateListRes> cateDetailList(@Validated @RequestBody CateListReq cateListReq){
        Map<String, Object> reqMap = new HashMap<>();

        String userId = cateListReq.getUserId().toUpperCase();
        String cateCode = cateListReq.getCateCode().toUpperCase();
        Optional<Integer> page = Optional.ofNullable(cateListReq.getPage());
        Optional<Integer> pageSize = Optional.ofNullable(cateListReq.getPageingSize());
        Pageable pageable = PageRequest.of(page.orElse(0), pageSize.orElse(10));

        reqMap.put("userId", userId);
        reqMap.put("cateCode", cateCode);

        log.info("[{}][{}] 상세 카테고리 목록 조회", strCRLF(userId), strCRLF(cateCode));

        CateListRes cateRes = cateService.cateDetailList(reqMap, pageable);

        return new ResponseEntity<>(cateRes, HttpStatus.OK);
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
