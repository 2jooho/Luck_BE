//package com.example.luck_project.common.config.jwt;
//
//import com.example.luck_project.common.config.ApiSupport;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Base64;
//import java.util.Date;
//import java.util.List;
//
//// 토큰을 생성하고 검증하는 클래스입니다.
//// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
//@RequiredArgsConstructor
//@Component
//public class JwtTokenProvider extends ApiSupport {
//    private String secretKey = "myprojectsecret";
//
//    // 토큰 유효시간 30분
//    private long tokenValidTime = 1 * 60 * 1000L;
//
//    private final UserDetailsService userDetailsService;
//
//    // 객체 초기화, secretKey를 Base64로 인코딩한다.
//    @PostConstruct
//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }
//
//    // JWT 토큰 생성
//    public String createToken(String userPk, List<String> roles) {
//        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
//        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
//        Date now = new Date();
//        return Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발행 시간 정보
//                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
//                // signature 에 들어갈 secret값 세팅
//                .compact();
//    }
//
//    // JWT 토큰에서 인증 정보 조회
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
//        logger.info("jwt 토큰 인증정보 : {}/ getAuthorities: {}", userDetails, userDetails.getAuthorities());
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }
//
//    // 토큰에서 회원 정보 추출
//    public String getUserPk(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//    }
//
//    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("Authorization");
//    }
//
//    // 토큰의 유효성 + 만료일자 확인
//    public boolean validateToken(String jwtToken) {
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
//            logger.info("jwt body : {}/ 유효성+만료일장 : {}", claims.getBody(), !claims.getBody().getExpiration().before(new Date()));
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//}
//
