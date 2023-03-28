package com.example.luck_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//클라이언트로 보낼 jwtToken, accessToken등이 담긴 객체
@Getter
@Setter
@AllArgsConstructor
public class GetSocialOAuthRes {
    private String user_id;
    private String accessToken; // 애플리케이션이 Google API 요청을 승인하기 위해 보내는 토큰
    private String tokenType;   // 반환된 토큰 유형(Bearer 고정)

}