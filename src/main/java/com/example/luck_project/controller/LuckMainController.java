package com.example.luck_project.controller;

import com.example.luck_project.controller.constants.BaseController;
import com.example.luck_project.dto.request.MainReq;
import com.example.luck_project.dto.response.MainRes;
import com.example.luck_project.service.MainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.luck_project.controller.constants.ApiUrl.*;

@RestController
@RequestMapping(BASE_URL)
@Slf4j
@Api(tags = {"메인 조회 API"})
public class LuckMainController extends BaseController {

    @Autowired
    private MainService mainService;

    @PostMapping(MAIN_URL)
    @ApiOperation(value = "메인 페이지 조회 API", notes = "메인 페이지 구성에 필요한 정보 응답")
    public ResponseEntity<MainRes> luckMain(@Validated @RequestBody MainReq mainReq){
        String userId = mainReq.getUserId().toUpperCase();

        log.info("[{}] 메인 조회 컨트롤러", userId);
        MainRes mainRes = mainService.main(userId);

        return new ResponseEntity<>(mainRes, getSuccessHeaders(), HttpStatus.OK);
    }

}