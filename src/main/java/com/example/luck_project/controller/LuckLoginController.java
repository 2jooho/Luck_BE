package com.example.luck_project.controller;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.config.jwt.JwtTokenProvider2;
import com.example.luck_project.common.util.SecurityUtil;
import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.TokenInfo;
import com.example.luck_project.dto.request.LoginReq;
import com.example.luck_project.dto.response.LoginRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import com.example.luck_project.repository.UserInfoRepository;
import com.example.luck_project.service.LoginService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/luck")
@RequiredArgsConstructor
public class LuckLoginController extends ApiSupport {

    @Autowired
    private LoginService loginService;

    private final JwtTokenProvider2 jwtTokenProvider;
    private final UserInfoRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login2")
    public TokenInfo login2(@RequestBody Map<String, String> user) {
        String memberId = user.get("userId");
        String password = user.get("password");
        TokenInfo tokenInfo = loginService.loginTest(memberId, password);
        return tokenInfo;
    }

    @PostMapping("/test")
    public String test(){
         String userId = SecurityUtil.getCurrentMemberId();
        return "<h1>test 통과 "+ userId +"</h1>";
    }

    UserEntity user = UserEntity.builder()
            .userId("test")
            .userPw("1111")
            .userName("userName")
            .nickName("testet")
            .birth("940520")
            .birthFlag("1")
            .birthTime("1602")
            .cateCodeList("03")
            .sex("1")
            .rgsttDtm(LocalDateTime.now())
            .roles(Collections.singletonList("USER")) // 최초 가입시 USER 로 설정
            .build();

    @PostMapping("/join")
    public String join(){
        logger.info("로그인 시도됨 : {}");

        userRepository.save(user);

        return user.toString();
    }

    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> user) {
//        logger.info("user userId = {}", user.get("userId"));
//        UserEntity member = (UserEntity) userRepository.findByUserId(user.get("userId"))
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//
//        logger.info("getRoles : {}", member.getRoles());
//        return jwtTokenProvider.createToken(member.getUserId(), member.getRoles());
//    }
//
//    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
//    private UserDetails createUserDetails(UserEntity member) {
//        return User.builder()
//                .username(member.getUsername())
//                .password(passwordEncoder.encode(member.getPassword()))
//                .roles(member.getRoles().toArray(new String[0]))
//                .build();
//    }



    @PostMapping("/auth/login")
    public ResponseEntity<LoginRes> luckLogin(@Validated @RequestBody LoginReq loginReq){
        String userId = loginReq.getUserId().toUpperCase();

        boolean isError = false;
        if(! (StringUtils.equals("1", loginReq.getOsType()) || StringUtils.equals("2", loginReq.getOsType()) || StringUtils.equals("9", loginReq.getOsType()))){
            logger.info("단말기 osType 에러");
            isError = true;
        }
        if(! (StringUtils.equals("M", loginReq.getOsType()) || StringUtils.equals("A", loginReq.getOsType()) )){
            logger.info("로그인 타입 에러");
            isError = true;
        }

        if(isError){
            throw new CustomException(ErrorCode.VALIDATION_FAIL);
        }

        logger.info("[{}] 로그인 컨트롤러", userId);
        LoginRes loginRes = loginService.login(loginReq);

        return new ResponseEntity<>(loginRes, HttpStatus.OK);
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