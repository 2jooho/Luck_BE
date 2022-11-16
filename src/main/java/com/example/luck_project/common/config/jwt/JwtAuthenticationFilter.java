package com.example.luck_project.common.config.jwt;

import com.example.luck_project.dto.TokenInfo;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        // 1. Request Header 에서 JWT 토큰 추출
//        String token = resolveToken((HttpServletRequest) request);

        // 2. validateToken 으로 토큰 유효성 검사
//        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        chain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT 를 받아옵니다.
        String accessToken = resolveToken(request);
        String refreshToken = resolveRefreshToken(request);
        System.out.println("accessToken: "+ accessToken + "/refreshToken :"+ refreshToken);
        // 유효한 토큰인지 확인합니다.
        if (accessToken != null) {
            // 어세스 토큰이 유효한 상황
            if (jwtTokenProvider.validateToken(accessToken)) {
                this.setAuthentication(accessToken);
            }
            // 어세스 토큰이 만료된 상황 | 리프레시 토큰 또한 존재하는 상황
            else if (!jwtTokenProvider.validateToken(accessToken) && refreshToken != null) {
                System.out.println("accessToken: "+ accessToken + "//refreshToken :"+ refreshToken);
                // 재발급 후, 컨텍스트에 다시 넣기
                /// 리프레시 토큰 검증
                boolean validateRefreshToken = jwtTokenProvider.validateToken(refreshToken);
                System.out.println("validateRefreshToken: "+ validateRefreshToken);
                /// 리프레시 토큰 저장소 존재유무 확인
                boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);
                System.out.println("isRefreshToken: "+ isRefreshToken);

                if (validateRefreshToken && isRefreshToken) {
                    /// 리프레시 토큰으로 이메일 정보 가져오기
                    String userId = jwtTokenProvider.getUserId(refreshToken);
                    /// 이메일로 권한정보 받아오기
                    List<String> roles = jwtTokenProvider.getRoles(userId);
                    if(userId.isBlank() || roles.size() < 1 || roles == null){
                        throw new CustomException(ErrorCode.RE_TOKEN_RESPONSE);
                    }

                    System.out.println("userId: "+ userId+ "/ roles : "+ roles);
                    //토큰 발급
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                    if(!userId.equals(authentication.getName())){
                        throw new CustomException(ErrorCode.RE_TOKEN_RESPONSE);
                    }
                    TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
                    /// 헤더에 어세스 토큰 추가
                    jwtTokenProvider.setHeaderAccessToken(response, tokenInfo.getAccessToken());
                    jwtTokenProvider.setHeaderRefreshToken(response, tokenInfo.getRefreshToken());
                    /// 컨텍스트에 넣기
                    this.setAuthentication(tokenInfo.getAccessToken());
                }
                response.setHeader("resultCode", String.valueOf(ErrorCode.RE_TOKEN_RESPONSE.getStatus()));
                response.setHeader("resultMessage", URLEncoder.encode(ErrorCode.RE_TOKEN_RESPONSE.getMessage(), "UTF-8"));
                throw new CustomException(ErrorCode.RE_TOKEN_RESPONSE);
            }
        }
        filterChain.doFilter(request, response);
    }

    // SecurityContext 에 Authentication 객체를 저장합니다.
    public void setAuthentication(String token) {
        // 토큰으로부터 유저 정보를 받아옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        // SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Request Header 에서 액세스 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Request의 Header에서 RefreshToken 값을 가져옵니다. "authorization" : "token'
    public String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("refreshToken");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
