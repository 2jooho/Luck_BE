package com.example.luck_project.controller;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.MainReq;
import com.example.luck_project.dto.MainRes;
import com.example.luck_project.dto.TodayLuckInfoDto;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.TodayLuckInfoRepository;
import com.example.luck_project.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/luck")
@RequiredArgsConstructor
@Slf4j
public class LuckMainController {

    @Autowired
    private MainService mainService;

    @PostMapping("/main.do")
    @ExceptionHandler
    public ResponseEntity<MainRes> luckMain(@Validated @RequestBody MainReq mainReq){
        String userId = mainReq.getUserId();

         MainRes mainRes = mainService.main(userId);

        return new ResponseEntity<>(mainRes, HttpStatus.OK);
    }

}
