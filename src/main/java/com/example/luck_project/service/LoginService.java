package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.config.jwt.JwtTokenProvider2;
import com.example.luck_project.common.util.PropertyUtil;
import com.example.luck_project.dto.TokenInfo;
import com.example.luck_project.dto.request.LoginReq;
import com.example.luck_project.dto.response.LoginRes;
import com.example.luck_project.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService extends ApiSupport {

    @Autowired
    private PropertyUtil propertyUtil;
    private final UserInfoRepository userInfoRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider2 jwtTokenProvider2;

    @Transactional
    public TokenInfo loginTest(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider2.generateToken(authentication);

        return tokenInfo;
    }






    @Transactional
    public LoginRes login(LoginReq loginReq) {
        LoginRes loginRes = new LoginRes();
        String userId = loginReq.getUserId();
        boolean loginFlag = false;

        logger.info("[{}] 계정 잠금 체크 및 휴면계정 체크", userId);
        // 로그인 실패 카운트 10회 이상일 경우 로그인 실패
        // 마지막 로그인 기준 1년이 지난 회원인 경우 휴면계정

        // 패스워드 SHA256 암호화
        logger.info("[{}] 고객 정보 조회", userId);
        // 패스워드가 sha256 암호화와 일치하는지 확인

        // 로그인 실패 시 실패 이력 등록
        if(!loginFlag){

            // 로그인 성공 시 성공 이력 등록
            // 모든 실패 이력 상태값 변경
        }else{

        }

        logger.info("[{}] 단말기id 조회", userId);
        // 전달받은 단말기id와 기존 db 단말기id 다른 경우 사용자 기기정보 업데이트
        // db 단말기id 정보가 없는 경우 신규 사용자 기기정보 등록

        logger.info("[{}] 마지막 로그인 시점 업데이트", userId);
        if(loginFlag){

        }

        return loginRes;
    }

}
