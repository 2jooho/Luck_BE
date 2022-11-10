package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.config.Oauth.Constant;
import com.example.luck_project.common.config.Oauth.GoogleOauth;
import com.example.luck_project.common.config.Oauth.KakaoOauth;
import com.example.luck_project.dto.GoogleOAuthToken;
import com.example.luck_project.dto.GoogleUser;
import com.example.luck_project.dto.KakaoOAuthToken;
import com.example.luck_project.dto.KakaoUser;
import com.example.luck_project.dto.response.GetSocialOAuthRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService extends ApiSupport {
    private final GoogleOauth googleOauth;
    private final KakaoOauth kakaoOauth;
    private final HttpServletResponse response;


//    /**
//     * 구글 로그인 리다이렉트
//     * @param authCode
//     * @return
//     */
//    public GoogleLoginDto googleRedirect(String authCode){
//
//        GoogleLoginDto userInfoDto = new GoogleLoginDto();
//
//        // HTTP 통신을 위해 RestTemplate 활용
//        RestTemplate restTemplate = new RestTemplate();
//        GoogleLoginReq requestParams = GoogleLoginReq.builder()
//                .clientId(configUtils.getGoogleClientId())
//                .clientSecret(configUtils.getGoogleSecret())
//                .code(authCode)
//                .redirectUri(configUtils.getGoogleRedirectUri())
//                .grantType("authorization_code")
//                .build();
//
//        try{
//            // Http Header 설정
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<GoogleLoginReq> httpRequestEntity = new HttpEntity<>(requestParams, headers);
//            ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(configUtils.getGoogleAuthUrl() + "/token", httpRequestEntity, String.class);
//
//            // ObjectMapper를 통해 String to Object로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
//            GoogleLoginRes googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginRes>() {});
//
//            // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
//            String jwtToken = googleLoginResponse.getIdToken();
//
//            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
//            String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.getGoogleAuthUrl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();
//
//            String resultJson = restTemplate.getForObject(requestUrl, String.class);
//            if(resultJson != null){
//                userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDto>() {});
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        return userInfoDto;
//    }





    /**
     * 소셜 로그인 요청
     * @param socialLoginType
     * @throws IOException
     */
    public void oAuthRequest(Constant.SocialLoginType socialLoginType) throws IOException {
        String redirectURL = ""; //리다이렉트 URL
        switch (socialLoginType){
            case GOOGLE:{
                //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스이다.
                redirectURL= googleOauth.getOauthRedirectURL();
            }break;
            case KAKAO:{
                //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스이다.
//                redirectURL= googleOauth.getOauthRedirectURL();
            }break;
            default:{
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }

        response.sendRedirect(redirectURL);
    }

    /**
     * 소셜 로그인 인증
     * @param socialLoginType
     * @param code
     * @return
     * @throws IOException
     */
    public GetSocialOAuthRes oAuthLogin(Constant.SocialLoginType socialLoginType, String code) throws IOException {
        GetSocialOAuthRes getSocialOAuthRes;
        switch (socialLoginType){
            case GOOGLE:{
                //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
                ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToken(code);

                //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
                GoogleOAuthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

                //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
                ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(oAuthToken);

                //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
                GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);
                //
                String user_id = googleUser.getEmail();

                //우리 서버의 db와 대조하여 해당 user가 존재하는 지 확인한다.
//                int user_num=accountProvider.getUserNum(user_id);

//                if(user_num!=0){
//                    //서버에 user가 존재하면 앞으로 회원 인가 처리를 위한 jwtToken을 발급한다.
//                    String jwtToken=jwtService.createJwt(user_num,user_id);
//                    //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
//                    GetSocialOAuthRes getSocialOAuthRes=new GetSocialOAuthRes(jwtToken,user_num,oAuthToken.getAccess_token(),oAuthToken.getToken_type());
//                    return getSocialOAuthRes;
//                }
//                else {
//                    throw new CustomException(ErrorCode.BAD_REQUEST);
//                }

                if(!user_id.isBlank()){
                    getSocialOAuthRes = new GetSocialOAuthRes(null,0, oAuthToken.getAccess_token(), oAuthToken.getToken_type());
                    return getSocialOAuthRes;
                }else {
                    throw new CustomException(ErrorCode.BAD_REQUEST);
                }
            }
            case KAKAO:{
                //카카오로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
                ResponseEntity<String> accessTokenResponse = kakaoOauth.requestAccessToken(code);

                //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
                KakaoOAuthToken oAuthToken = kakaoOauth.getAccessToken(accessTokenResponse);

                //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
                ResponseEntity<String> userInfoResponse = kakaoOauth.requestUserInfo(oAuthToken);

                //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
                KakaoUser kakaoUser = kakaoOauth.getUserInfo(userInfoResponse);

                String user_id = kakaoUser.getEmail();

                if(!user_id.isBlank()){
                    getSocialOAuthRes = new GetSocialOAuthRes(null,0, oAuthToken.getAccess_token(), oAuthToken.getToken_type());
                    return getSocialOAuthRes;
                }else {
                    throw new CustomException(ErrorCode.BAD_REQUEST);
                }

            }
            default:{
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
    }


}