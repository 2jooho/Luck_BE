package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.config.jwt.JwtTokenProvider;
import com.example.luck_project.common.util.RedisUtil;
import com.example.luck_project.domain.RefreshTokenEntity;
import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.domain.UserLoginHistoryEntity;
import com.example.luck_project.domain.UserMobileDeviceEntity;
import com.example.luck_project.dto.TokenInfo;
import com.example.luck_project.dto.request.LoginReq;
import com.example.luck_project.dto.response.LoginRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.LoginHistoryRepository;
import com.example.luck_project.repository.TokenRepository;
import com.example.luck_project.repository.UserInfoRepository;
import com.example.luck_project.repository.UserMobileDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.luck_project.constants.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LoginService extends ApiSupport {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final HttpServletResponse response;
//    private final RedisUtil redisUtil;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private UserMobileDeviceRepository userMobileDeviceRepository;

    @Transactional
    public TokenInfo loginTest(String memberId, String id) {
        log.info("[{}][{}] 로그인 로직 진행", memberId, id);
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, id);
        log.info("[{}][{}][{}] 인증 여부 : {}", memberId, passwordEncoder.encode(id), authenticationToken);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("[{}][{}][{}] 유저 정보 확인 : {}", memberId, passwordEncoder.encode(id), authentication);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        String accessToken = tokenInfo.getAccessToken();
        String refreshToken = tokenInfo.getRefreshToken();
        log.info("[{}][{}][{}] 토큰 확인", memberId, accessToken, refreshToken);
        // 리프레시 토큰 저장소에 저장
        tokenRepository.save(new RefreshTokenEntity(refreshToken));


        return tokenInfo;
    }


    public void createToken(String userId, String password, HttpServletResponse response, Map<String, String> paramMap) {
        TokenInfo tokenInfo;
        String loginType = paramMap.getOrDefault("loginType", "");
        if (loginType.equals("oAuth")) {
            log.info("[{}][{}] 외부인증 토큰 로직 진행", userId, password);
            tokenInfo = jwtTokenProvider.oAuthCreateToken(userId, paramMap.getOrDefault("role", "USER"));

        } else {
            log.info("[{}][{}] 로그인 로직 진행", userId, password);
            // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
            // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
            log.info("[{}][{}][{}] 인증 여부 ", userId, passwordEncoder.encode(password), authenticationToken);
            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            log.info("[{}][{}][{}] 유저 정보 확인 : {}", userId, passwordEncoder.encode(password), authentication);
            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            tokenInfo = jwtTokenProvider.generateToken(authentication);
        }

        String accessToken = tokenInfo.getAccessToken();
        String refreshToken = tokenInfo.getRefreshToken();
        // 리프레시 토큰 저장소에 저장

//        redisUtil.setExpireValue("refresh"+userId, tokenInfo.getRefreshToken(), (24 * 60 * 60 * 1000L));
        tokenRepository.save(new RefreshTokenEntity(refreshToken));
        log.info("[{}][{}][{}] 토큰 확인", userId, accessToken, refreshToken);

        /// 헤더에 어세스 토큰 추가
        jwtTokenProvider.setHeaderAccessToken(response, tokenInfo.getAccessToken());
        jwtTokenProvider.setHeaderRefreshToken(response, tokenInfo.getRefreshToken());

    }


    /**
     * JWT 로그인
     *
     * @param loginReq
     * @return
     */
    @Transactional
    public LoginRes login(LoginReq loginReq) {
        LoginRes loginRes = new LoginRes();
        String userId = loginReq.getUserId().toUpperCase();
        String password = loginReq.getPassword();
        boolean loginFlag = false;
        String loginType = "basic";
        String passwdUpdateYn = "N";

        log.info("[{}] 고객 정보 조회: pw : {}", userId, passwordEncoder.encode(password));
        Optional<UserEntity> userEntity = userInfoRepository.findByUserId(userId);
        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        log.info("[{}][{}] 계정 잠금 체크 및 휴면계정 체크", userId, password);
        // 로그인 실패 카운트 10회 이상일 경우 로그인 실패
        int loginCount = loginHistoryRepository.countByUserIdAndLoginIscode(userId, "X").get();
        System.out.println("loginCount:" + loginCount);
        //로그인 횟수 10회 이상
        if (loginCount >= 10) {
            throw new CustomException(LOGIN_COUNT_FAIL);
        }
        // 마지막 로그인 기준 1년이 지난 회원인 경우 휴면계정
        String userFlag = userEntity.get().getInactivityFlag();
        System.out.println("userFlag:" + userFlag);
        if (userFlag != null && userFlag.equals("Y")) {
            throw new CustomException(LOGIN_AUTH_FAIL);
        }
        // 패스워드 암호화 상태로 비교
        log.info("패스워드 비교");
        loginFlag = passwordEncoder.matches(password, userEntity.get().getPassword()); //비암호화, 암호화
        System.out.println("loginFlag:" + loginFlag);
        // 로그인 실패 시 실패 이력 등록
        if (!loginFlag) {
            System.out.println("로그인 실패");
            log.info("로그인 실패");
            UserLoginHistoryEntity loginHistoryEntity = new UserLoginHistoryEntity().builder()
                    .userId(userId)
                    .loginIscode("X")
                    .loginDate(LocalDateTime.now())
                    .build();
            loginHistoryRepository.save(loginHistoryEntity);
            System.out.println("로그인 실패 이력 등록 성공");
            // 로그인 성공 시 성공 이력 등록
            // 모든 실패 이력 상태값 변경
        } else {
            System.out.println("로그인 성공");
            log.info("로그인 성공");
            UserLoginHistoryEntity loginHistoryEntity = new UserLoginHistoryEntity().builder()
                    .userId(userId)
                    .loginIscode("Y")
                    .loginDate(LocalDateTime.now())
                    .build();
            loginHistoryRepository.save(loginHistoryEntity);
            System.out.println("로그인 이력 등록 성공");
            //실패 이력 상태값 변경
            Optional<List<UserLoginHistoryEntity>> userLoginHistoryEntity = loginHistoryRepository.findByUserIdAndLoginIscode(userId, "X");
            if (userLoginHistoryEntity.isPresent()) {
                userLoginHistoryEntity.get().stream().forEach((historyEntity)-> {
                    historyEntity.iscodeUpdate("I");
                });
                System.out.println("로그인 이력 수정 성공");
            }
        }

        log.info("[{}] 단말기id 조회", userId);
        // 전달받은 단말기id와 기존 db 단말기id 다른 경우 사용자 기기정보 업데이트
        // db 단말기id 정보가 없는 경우 신규 사용자 기기정보 등록
        Optional<UserMobileDeviceEntity> deviceEntity = userMobileDeviceRepository.findTop1ByUserIdOrderByIdDesc(userId);
        System.out.println("단말기 id 정보 조회 : " + deviceEntity);
        // 단말기 id가 존재하는 경우
        if (deviceEntity.isPresent()) {
            if (deviceEntity.get().getDeviceId().equals(loginReq.getDeviceId())) {
                log.info("단말기 일치");
            } else {
                log.info("단말기 업데이트");
                UserMobileDeviceEntity userMobileDeviceEntity = new UserMobileDeviceEntity().builder()
                        .userId(userId)
                        .deviceId(loginReq.getDeviceId())
                        .deviceType(loginReq.getOsType())
                        .build();

                userMobileDeviceRepository.save(userMobileDeviceEntity);
            }
            //신규 단말기 등록
        } else {
            log.info("단말기 등록");
            UserMobileDeviceEntity userMobileDeviceEntity = new UserMobileDeviceEntity().builder()
                    .userId(userId)
                    .deviceId(loginReq.getDeviceId())
                    .deviceType(loginReq.getOsType())
                    .rgsttDtm(LocalDateTime.now())
                    .build();
            userMobileDeviceRepository.save(userMobileDeviceEntity);
        }

        System.out.println("비밀번호 갱신 공지 여부");
        log.info("[{}] 비밀번호 갱신 공지 여부", userId);
        //비밀번호 변경 후 180일이 지난 경우 공지
        LocalDateTime nowDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String passModDt = userEntity.get().getPassModDt();
        LocalDateTime date = LocalDateTime.parse(passModDt, formatter);
        System.out.println("date:" + date);
        date = date.plusDays(180);
        System.out.println("date:" + date);
        //180일이 지난 시점이 오늘이거나 지난 경우
        if (date.isEqual(nowDate) || date.isBefore(nowDate)) {
            passwdUpdateYn = "Y";
        }

        log.info("[{}] 마지막 로그인 시점 업데이트", userId);
        if (loginFlag) {
            String todayDate = nowDate.format(formatter);
            userEntity.get().lastLoginDtUpdate(todayDate, nowDate, "API");

            System.out.println("토큰 생성 진행");
            log.info("[{}] 토큰 생성", userId);
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("loginType", "basic");
            this.createToken(userId, password, response, paramMap);
        }
        loginRes.setUserId(userId);
        loginRes.setNickName(userEntity.get().getNickName());
        loginRes.setUserName(userEntity.get().getUsername());
        loginRes.setPasswdUpdateYn(passwdUpdateYn);

        return loginRes;
    }

}
