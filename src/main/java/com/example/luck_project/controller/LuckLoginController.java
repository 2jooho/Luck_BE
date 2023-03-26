package com.example.luck_project.controller;

import com.example.luck_project.common.config.jwt.JwtTokenProvider;
import com.example.luck_project.common.util.SecurityUtil;
import com.example.luck_project.controller.constants.BaseController;
import com.example.luck_project.dto.TokenInfo;
import com.example.luck_project.dto.request.LoginReq;
import com.example.luck_project.dto.response.LoginRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.UserInfoRepository;
import com.example.luck_project.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.example.luck_project.constants.ResponseCode.VALIDATION_FAIL;
import static com.example.luck_project.controller.constants.ApiUrl.*;


@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Validated
@Slf4j
@CrossOrigin(origins = "http://127.0.0.1:3000/")  // 컨트롤러 클래스의 상단
public class LuckLoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserInfoRepository userRepository;


    @PostMapping("/login2")
    public ResponseEntity<TokenInfo> login2(@RequestBody Map<String, String> user, HttpServletResponse response) {
        String memberId = user.get("userId");
        String password = user.get("password");
        TokenInfo tokenInfo = loginService.loginTest(memberId, password);
        jwtTokenProvider.setHeaderAccessToken(response, tokenInfo.getAccessToken());
        jwtTokenProvider.setHeaderRefreshToken(response, tokenInfo.getRefreshToken());

        return new ResponseEntity<>(tokenInfo, HttpStatus.OK);
    }

    @PostMapping("/retokenTest")
    public ResponseEntity  retokenTest(@RequestBody Map<String, String> user, HttpServletResponse response){
         String userId = SecurityUtil.getCurrentMemberId();
//1        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
//1        DecodedJWT decodedJWT = verifier.verify(accessToken);

//2        String bearerToken = request.getHeader("Authorization");
//2        if (org.springframework.util.StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
//2            bearerToken = bearerToken.substring(7);
//2        }
//2        Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
//2        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/test")
    public String test(){
        String userId = SecurityUtil.getCurrentMemberId();

        return "성공성공 / userId :"+ userId;
    }

//    UserEntity user = UserEntity.builder()
//            .userId("test")
//            .userPw("1111")
//            .userName("userName")
//            .nickName("testet")
//            .birth("940520")
//            .birthFlag("1")
//            .birthTime("1602")
//            .cateCodeList("03")
//            .sex("M")
//            .rgsttDtm(LocalDateTime.now())
//            .roles(Collections.singletonList("USER")) // 최초 가입시 USER 로 설정
//            .build();

//    @PostMapping("/join")
//    public String join(){
//        log.info("로그인 시도됨 : {}");
//
//        userRepository.save(user);
//
//        return user.toString();
//    }

    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> user) {
//        log.info("user userId = {}", user.get("userId"));
//        UserEntity member = (UserEntity) userRepository.findByUserId(user.get("userId"))
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//
//        log.info("getRoles : {}", member.getRoles());
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


    /**
     * 로그인
     * @param loginReq
     * @return
     */
    @PostMapping(LOGIN_URL)
    public ResponseEntity<LoginRes> luckLogin(@RequestBody LoginReq loginReq){
        String userId = loginReq.getUserId().toUpperCase();

        boolean isError = false;
        if(! (StringUtils.equals("1", loginReq.getOsType()) || StringUtils.equals("2", loginReq.getOsType()) || StringUtils.equals("9", loginReq.getOsType()))){
            log.info("단말기 osType 에러");
            isError = true;
        }
        if(! (StringUtils.equals("M", loginReq.getLoginType()) || StringUtils.equals("A", loginReq.getLoginType()) )){
            log.info("로그인 타입 에러");
            isError = true;
        }
        if(!StringUtils.equals("B", loginReq.getLoginDvsn())){
            log.info("로그인 구분 에러");
            isError = true;
        }

        if(isError){
            throw new CustomException(VALIDATION_FAIL);
        }

        log.info("[{}] 로그인 컨트롤러", userId);
        LoginRes loginRes = loginService.login(loginReq);

        return new ResponseEntity<>(loginRes, getSuccessHeaders(), HttpStatus.OK);
    }


//    /**
//     * 토큰 재발급
//     * @param user
//     * @return
//     */
//    @PostMapping("/auth/reissue/token")
//    public ResponseEntity<TokenInfo> luckLogin(@RequestBody Map<String, String> user) {
//        String memberId = user.get("userId");
//        String password = user.get("password");
//        TokenInfo tokenInfo = loginService.loginTest(memberId, password);
//        return new ResponseEntity<>(tokenInfo, HttpStatus.OK);
//    }

}