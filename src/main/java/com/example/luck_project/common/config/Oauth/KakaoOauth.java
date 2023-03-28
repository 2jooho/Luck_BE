package com.example.luck_project.common.config.Oauth;


import com.example.luck_project.dto.KakaoOAuthToken;
import com.example.luck_project.dto.KakaoUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {
    // applications.yml 에서 value annotation을 통해서 값을 받아온다.
    @Value("${OAuth2.kakao.url}")
    private String KAKAO_SNS_LOGIN_URL;

    @Value("${OAuth2.kakao.client-id}")
    private String KAKAO_SNS_CLIENT_ID;

    @Value("${OAuth2.kakao.callback-url}")
    private String KAKAO_SNS_CALLBACK_URL;

    @Value("${OAuth2.kakao.client-secret}")
    private String KAKAO_SNS_CLIENT_SECRET;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    /**
     * 소셜로그인 리다이렉트 url
     * @return
     */
    @Override
    public String getOauthRedirectURL() {

        Map<String, Object> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", KAKAO_SNS_CLIENT_ID);
        params.put("redirect_uri", KAKAO_SNS_CALLBACK_URL);

        //parameter를 형식에 맞춰 구성해주는 함수
        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        String redirectURL = KAKAO_SNS_LOGIN_URL + "?" + paramStr;
        System.out.println("redirectURL = " + redirectURL);

        return redirectURL;
        /*
         * https://accounts.google.com/o/oauth2/v2/auth?scope=profile&response_type=code
         * &client_id="할당받은 id"&redirect_uri="access token 처리")
         * 로 Redirect URL을 생성하는 로직을 구성
         * */
    }

    public ResponseEntity<String> requestAccessToken(String code) {
        String GOOGLE_TOKEN_REQUEST_URL="https://kauth.kakao.com/oauth/token";

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", KAKAO_SNS_CLIENT_ID);
        params.put("redirect_uri", KAKAO_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity;
        }

        return null;
    }

    /**
     * 인증 토큰 추출
     * @param response
     * @return
     * @throws JsonProcessingException
     */
    public KakaoOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("response.getBody() = " + response.getBody());

        KakaoOAuthToken kakaoOAuthToken = objectMapper.readValue(response.getBody(), KakaoOAuthToken.class);

        return kakaoOAuthToken;
    }

    /**
     * 인증 accessToken으로 유저 정보 조회
     * @param oAuthToken
     * @return
     */
    public ResponseEntity<String> requestUserInfo(KakaoOAuthToken oAuthToken) {
        String GOOGLE_USERINFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

        //header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + oAuthToken.getAccess_token());

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.POST, request, String.class);
        System.out.println("response.getBody() = " + response.getBody());
        return response;
    }

    /**
     * 소셜 유저 정보 추출
     * @param userInfoRes
     * @return
     * @throws JsonProcessingException
     */
    public KakaoUser getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException {
        KakaoUser kakaoUser = new KakaoUser();
        Map<String, String> kakaoUserMap = objectMapper.readValue(userInfoRes.getBody(), Map.class);
        Map<String, String> kakaoAccountMap = objectMapper.readValue(kakaoUserMap.get("kakao_account"), Map.class);
        kakaoUser.setId(kakaoUserMap.get("id"));
        kakaoUser.setName(kakaoAccountMap.get("name"));
        kakaoUser.setEmail(kakaoAccountMap.get("email"));

        return kakaoUser;
    }


}