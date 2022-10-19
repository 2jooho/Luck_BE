package com.example.luck_project.controller;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.MainReq;
import com.example.luck_project.dto.MainRes;
import com.example.luck_project.dto.UserInfoDto;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/luck")
@Log4j2
public class LuckMainController {

    @Autowired
    private MainService mainService;

    @PostMapping("/main.do")
    public ResponseEntity<MainRes> luckMain(@Validated @RequestBody MainReq mainReq){
        String userId = mainReq.getUserId().toUpperCase();

        log.info("[{}] 메인 조회 컨트롤러", userId);
        MainRes mainRes = mainService.main(userId);

        return new ResponseEntity<>(mainRes, HttpStatus.OK);
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