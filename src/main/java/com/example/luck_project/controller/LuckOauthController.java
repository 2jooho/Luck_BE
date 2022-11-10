package com.example.luck_project.controller;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.config.Oauth.Constant;
import com.example.luck_project.dto.response.GetSocialOAuthRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/luck")
@RequiredArgsConstructor
public class LuckOauthController extends ApiSupport {

    @Autowired
    private OAuthService oAuthService;

//    /**
//     * 구글 로그인 리다이렉트(1-2)
//     * @param authCode
//     * @return
//     */
//    @PostMapping(value = "google/login/redirect")
//    public ResponseEntity<GoogleLoginDto> redirectGoogleLogin(@RequestParam(value = "code") String authCode){
//
//        GoogleLoginDto userInfoDto = oAuthService.googleRedirect(authCode);
//
//        return ResponseEntity.ok().body(userInfoDto);
//    }

    /**
     * 소셜 로그인으로 리다이렉트 해주는 url(2-1)
     * @param socialLoginPath
     * @throws IOException
     */
    @PostMapping("/auth/{socialLoginType}/login") //GOOGLE이 들어올 것이다.
    public void socialLoginRedirect(@PathVariable(name="socialLoginType") String socialLoginPath) throws IOException {
        //소셜 로그인 타입
        Constant.SocialLoginType socialLoginType = Constant.SocialLoginType.valueOf(socialLoginPath.toUpperCase());

        logger.info("[{}] 소셜 로그인", socialLoginType);
        oAuthService.oAuthRequest(socialLoginType);
    }

    /**
     * 소셜 로그인 콜백 후 로그인 처리(2-2)
     * @param socialLoginPath
     * @param authCode
     * @return
     * @throws IOException
     * @throws CustomException
     */
    @PostMapping(value = "/auth/{socialLoginType}/callback")
    public ResponseEntity<GetSocialOAuthRes> callback (
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String authCode) throws IOException, CustomException {

        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ authCode);
        Constant.SocialLoginType socialLoginType= Constant.SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes = oAuthService.oAuthLogin(socialLoginType, authCode);
        return ResponseEntity.ok().body(getSocialOAuthRes);
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