package com.example.luck_project.common.config.jwt;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.dto.TokenInfo;
import com.example.luck_project.repository.TokenRepository;
import com.example.luck_project.repository.UserInfoRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@Component
public class JwtTokenProvider extends ApiSupport {

    private final Key key;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    // 어세스 토큰 유효시간 | 10분
    private long accessTokenValidTime = 1 * 60 * 1000L;
    // 리프레시 토큰 유효시간 | 1일
    private long refreshTokenValidTime = 24 * 60 * 60 * 1000L;

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfo generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        authentication.getAuthorities().forEach(System.out::println);
        System.out.println("authorities:"+ authorities+"authentication.getName() :"+ authentication.getName());
        long now = (new Date()).getTime();
        // Access Token 생성
        //1일: 24 * 60 * 60 * 1000 = 86400000
        //1분: 1*60*1000 = 60000
        Date accessTokenExpiresIn = new Date(now + accessTokenValidTime);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities) //발행 유저 정보 저장
                .setExpiration(accessTokenExpiresIn) //토큰 유효 시간 저장
                .signWith(key, SignatureAlgorithm.HS256) //해싱 알고리즘 및 키 설정
                .compact(); //생성

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(now + refreshTokenValidTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Create token(2)
//    public String createToken(String userId, List<String> roles, String tokenType) {
//        Claims claims = Jwts.claims().setSubject(email); // claims 생성 및 payload 설정
//        claims.put("roles", roles); // 권한 설정, key/ value 쌍으로 저장
//
//        Date date = new Date();
//        return Jwts.builder()
//                .setSubject(userId)
//                .claim("auth", authorities) //발행 유저 정보 저장
//                .setExpiration(tokenType.equals("access") ? new Date(date.getTime() + accessTokenValidTime) : new Date(date.getTime() + refreshTokenValidTime)) // 토큰 유효 시간 저장
//                .signWith(key, SignatureAlgorithm.HS256) // 해싱 알고리즘 및 키 설정
//                .compact(); // 생성
//    }

    // Create token(3)
        public TokenInfo oAuthCreateToken(String userId, String roles) {
        Claims claims = Jwts.claims().setSubject(userId); // claims 생성 및 payload 설정
        claims.put("auth", "ROLE_"+roles); // 권한 설정, key/ value 쌍으로 저장
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessTokenValidTime);
        String accessToken = Jwts.builder()
                .setSubject(userId)
                .setClaims(claims) // 발행 유저 정보 저장
                .setExpiration(accessTokenExpiresIn) // 토큰 유효 시간 저장
                .signWith(key, SignatureAlgorithm.HS256) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(now + refreshTokenValidTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    // JWT 에서 인증 정보 조회
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        System.out.println("claims : "+claims.toString());
        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        authorities.stream().forEach(System.out::println);

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        System.out.println("principal : "+principal +"authorities :"+ authorities.stream().collect(Collectors.toList()).get(0));
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    //토큰의 유효성 + 만료일자 체크
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            System.out.println("validateToken : "+Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token));
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            logger.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            logger.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            logger.info("JWT claims string is empty.", e);
        }
        return false;
    }

    //토큰에서 회원 정보 추출
    private Claims parseClaims(String accessToken) {
        try {
            System.out.printf("parseClaims" + Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody());
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // Access 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "Bearer "+ accessToken);
    }

    // Refresh 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("RefreshToken", "Bearer "+ refreshToken);
    }

    // RefreshToken 존재유무 확인
    public boolean existsRefreshToken(String refreshToken) {
        return tokenRepository.existsByRefreshToken(refreshToken);
    }

    // id로 권한 정보 가져오기
    public List<String> getRoles(String userId) {
        return userInfoRepository.findByUserId(userId).get().getRoles();
    }

    // 토큰에서 회원 정보 추출
    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

}

