package com.example.luck_project.controller;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.dto.request.JoinReq;
import com.example.luck_project.dto.response.JoinRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import com.example.luck_project.service.JoinService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/luck")
public class LuckJoinController extends ApiSupport {
    @Autowired
    private JoinService joinService;

    /**
     * 회원가입
     * @param joinReq
     * @return
     */
    @PostMapping("/auth/join")
    public ResponseEntity<JoinRes> luckJoin(@Validated @RequestBody JoinReq joinReq){
        String userId = joinReq.getUserId().toUpperCase();

        logger.info("[{}] 회원가입 컨트롤러", userId);
        JoinRes joinRes = joinService.join(joinReq);

        return new ResponseEntity<>(joinRes, HttpStatus.OK);
    }

    /**
     * 아이디 중복 검사
     * @param userInfo
     * @return
     */
    @PostMapping("/auth/idCheck")
    public ResponseEntity luckIdCheck(@RequestBody Optional<Map<String, String>> userInfo){
        String userId = "";
        if(userInfo.isPresent()){
            userId = Optional.of(userInfo.get().get("userId")).get();
            if(userId.length() > 20){
                logger.info("[{}] userId는 20자리를 넘을 수 없습니다.", userId);
                throw new CustomException(ErrorCode.VALIDATION_FAIL);
            }
        }else{
            logger.info("[{}] userId는 필수 입니다.", userId);
            throw new CustomException(ErrorCode.VALIDATION_FAIL);
        }

        logger.info("[{}] 아이디 중복검사 컨트롤러", userId);
        joinService.idCheck(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 닉네임 중복 검사
     * @param userInfo
     * @return
     */
    @PostMapping("/auth/nickNameCheck")
    public ResponseEntity luckNickName(@RequestBody Optional<Map<String, String>> userInfo){
        String nickName = "";
        if(userInfo.isPresent()){
            nickName = Optional.of(userInfo.get().get("nickName")).get();
            if(nickName.length() > 10 && nickName.length() < 2){
                logger.info("[{}] nickName은 2~10자리 이내여야 합니다.", nickName);
                throw new CustomException(ErrorCode.VALIDATION_FAIL);
            }
        }else{
            logger.info("[{}] nickName은 필수 입니다.", nickName);
            throw new CustomException(ErrorCode.VALIDATION_FAIL);
        }

        logger.info("[{}] 닉네임 중복검사 컨트롤러", nickName);
        joinService.nickNameCheck(nickName);

        return new ResponseEntity<>(HttpStatus.OK);
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