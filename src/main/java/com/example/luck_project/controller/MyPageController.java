package com.example.luck_project.controller;

import com.example.luck_project.controller.constants.BaseController;
import com.example.luck_project.dto.request.MainReq;
import com.example.luck_project.dto.response.MainRes;
import com.example.luck_project.dto.response.MyPageRes;
import com.example.luck_project.dto.response.MyRecommandStarRes;
import com.example.luck_project.service.MainService;
import com.example.luck_project.service.MyPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.luck_project.controller.constants.ApiUrl.*;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"마이페이지 api"}) //swagger 소주제
public class MyPageController extends BaseController {

    private final MyPageService myPageService;

    @GetMapping(MYPAGE_URL)
    @ApiOperation(value = "마이페이지 조회 API", notes = "마이페이지 조회 구성에 필요한 정보 응답")
    public ResponseEntity<MyPageRes> myPageInfo(@RequestParam String userId){

        MyPageRes res = myPageService.getMyPageInfo(userId.toUpperCase());

        return new ResponseEntity<>(res, getSuccessHeaders(), HttpStatus.OK);
    }

    @GetMapping(RECOMMAND_URL)
    @ApiOperation(value = "마이 추천별 조회 API", notes = "마이 추천별 조회 구성에 필요한 정보 응답")
    public ResponseEntity<MyRecommandStarRes> myRecommandStar(@RequestParam String userId){

        MyRecommandStarRes res = myPageService.getMyRecommandStar(userId);

        return new ResponseEntity<>(res, getSuccessHeaders(), HttpStatus.OK);
    }

}
