package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.config.jwt.JwtTokenProvider;
import com.example.luck_project.domain.RefreshTokenEntity;
import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.TokenInfo;
import com.example.luck_project.dto.request.LoginReq;
import com.example.luck_project.dto.response.LoginRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import com.example.luck_project.repository.TokenRepository;
import com.example.luck_project.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService extends ApiSupport {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Transactional
    public TokenInfo loginTest(String memberId, String password) {
        logger.info("[{}][{}] 로그인 로직 진행", memberId, password);
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        logger.info("[{}][{}][{}] 인증 여부 : {}", memberId,passwordEncoder.encode(password), authenticationToken);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        logger.info("[{}][{}][{}] 유저 정보 확인 : {}", memberId, passwordEncoder.encode(password), authentication);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        String accessToken = tokenInfo.getAccessToken();
        String refreshToken = tokenInfo.getRefreshToken();
        logger.info("[{}][{}][{}] 토큰 확인", memberId, accessToken, refreshToken);
        // 리프레시 토큰 저장소에 저장
        tokenRepository.save(new RefreshTokenEntity(refreshToken));


        return tokenInfo;
    }


    public void createToken(String userId, String password, HttpServletResponse response){

        logger.info("[{}][{}] 로그인 로직 진행", userId, password);
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        logger.info("[{}][{}][{}] 인증 여부 : {}", userId,passwordEncoder.encode(password), authenticationToken);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        logger.info("[{}][{}][{}] 유저 정보 확인 : {}", userId, passwordEncoder.encode(password), authentication);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        String accessToken = tokenInfo.getAccessToken();
        String refreshToken = tokenInfo.getRefreshToken();
        logger.info("[{}][{}][{}] 토큰 확인", userId, accessToken, refreshToken);
        // 리프레시 토큰 저장소에 저장
        tokenRepository.save(new RefreshTokenEntity(refreshToken));
        /// 헤더에 어세스 토큰 추가
        jwtTokenProvider.setHeaderAccessToken(response, tokenInfo.getAccessToken());
        jwtTokenProvider.setHeaderRefreshToken(response, tokenInfo.getRefreshToken());
    }


    /**
     * JWT 로그인
     * @param loginReq
     * @return
     */
    @Transactional
    public LoginRes login(LoginReq loginReq, HttpServletResponse response) {
        LoginRes loginRes = new LoginRes();
        String userId = loginReq.getUserId().toUpperCase();
        String password = loginReq.getPassword();
        boolean loginFlag = false;

        logger.info("[{}][{}] 계정 잠금 체크 및 휴면계정 체크", userId, password);
        // 로그인 실패 카운트 10회 이상일 경우 로그인 실패

        // 마지막 로그인 기준 1년이 지난 회원인 경우 휴면계정

        // 패스워드 암호화 상태로 비교
        logger.info("[{}] 고객 정보 조회: pw : {}", userId, passwordEncoder.encode(password));
        Optional<UserEntity> userEntity = userInfoRepository.findByUserId(userId);
        userEntity.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        loginFlag = passwordEncoder.matches(password, userEntity.get().getPassword()); //비암호화, 암호화
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
            LocalDateTime nowDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String todayDate = nowDate.format(formatter);
            userEntity.get().lastLoginDtUpdate(todayDate, nowDate, "API");
        }

        this.createToken(userId, password, response);

        return loginRes;
    }

}
