package com.example.luck_project.controller;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.config.Oauth.Constant;
import com.example.luck_project.dto.request.JoinReq;
import com.example.luck_project.dto.request.SocialJoinReq;
import com.example.luck_project.dto.response.JoinRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import com.example.luck_project.service.JoinService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 소셜 회원가입
     * @param socialLoginPath
     * @param socialJoinReq
     * @return
     */
    @PostMapping("/auth/{socialLoginPath}/join")
    public ResponseEntity<JoinRes> luckSocialJoin(@PathVariable(name = "socialLoginPath") String socialLoginPath, @Validated @RequestBody SocialJoinReq socialJoinReq){
        String userId = socialJoinReq.getUserId().toUpperCase();

        logger.info("[{}][{}] 소셜 회원가입 컨트롤러", userId, socialLoginPath);

        Constant.SocialLoginType socialLoginType = Constant.SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        JoinRes joinRes = joinService.socialJoin(socialJoinReq, socialLoginType);

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
        String loginDvsn = "";
        if(userInfo.isPresent()){
            userId = Optional.of(userInfo.get().get("userId")).get().toUpperCase();
            loginDvsn = Optional.of(userInfo.get().get("loginDvsn")).get();
            if(userId.isBlank() || userId.length() > 20){
                logger.info("[{}] userId는 20자리를 넘을 수 없습니다.", userId);
                throw new CustomException(ErrorCode.VALIDATION_FAIL);
            }
            if(loginDvsn.isBlank() || !"BKG".contains(loginDvsn)){
                logger.info("[{}] loginDvsn 에러", loginDvsn);
                throw new CustomException(ErrorCode.VALIDATION_FAIL);
            }
        }else{
            logger.info("요청값이 존재하지 않습니다.");
            throw new CustomException(ErrorCode.VALIDATION_FAIL);
        }

        logger.info("[{}] 아이디 중복검사 컨트롤러", userId);
        joinService.idCheck(userId, loginDvsn);

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
     * 초기 회원 정보 설정
     * @param userInfo
     * @return
     */
    @PostMapping("/auth/userSetting")
    public ResponseEntity userSetting(@RequestBody Optional<Map<String, String>> userInfo){
        String userId = "";
        String loginDvsn = "";
        if(userInfo.isPresent()){
            userId = Optional.of(userInfo.get().get("userId")).get().toUpperCase();
            loginDvsn = Optional.of(userInfo.get().get("loginDvsn")).get();
            if(userId.isBlank() ){
                logger.info("userId는 필수 입니다.");
                throw new CustomException(ErrorCode.VALIDATION_FAIL);
            }else if(loginDvsn.isBlank() || !"BKG".contains(loginDvsn)){
                logger.info("[{}] loginDvsn는 필수 입니다.", loginDvsn);
                throw new CustomException(ErrorCode.VALIDATION_FAIL);
            }
        }else{
            logger.info("요청값이 존재하지 않습니다.");
            throw new CustomException(ErrorCode.VALIDATION_FAIL);
        }

        joinService.userSetting(null, userId, loginDvsn);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 회원탈퇴
     * @param userInfo
     * @return
     */
    @PostMapping("/auth/bolter")
    public ResponseEntity userBolter(@RequestBody Optional<Map<String, String>> userInfo) {
        String userId = "";
        String loginDvsn = "";

        if(userInfo.isPresent()){
            userId = Optional.of(userInfo.get().get("userId")).get().toUpperCase();
            loginDvsn = Optional.of(userInfo.get().get("loginDvsn")).get();
            if(userId.isBlank() ){
                logger.info("userId는 필수 입니다.");
                throw new CustomException(ErrorCode.VALIDATION_FAIL);
            }else if(loginDvsn.isBlank() || !"BKG".contains(loginDvsn)){
                logger.info("[{}] loginDvsn는 필수 입니다.", loginDvsn);
                throw new CustomException(ErrorCode.VALIDATION_FAIL);
            }
        }else{
            logger.info("요청값이 존재하지 않습니다.");
            throw new CustomException(ErrorCode.VALIDATION_FAIL);
        }

        joinService.userBolter(userId, loginDvsn);

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