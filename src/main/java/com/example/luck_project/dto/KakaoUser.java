package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 액세스 토큰을 보내 받아올 카카오에 등록된 사용자 정보
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KakaoUser {
    public String id;
    public String name;
    public String email;
}