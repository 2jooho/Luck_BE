package com.example.luck_project.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@ToString
@Valid
public class LoginReq {
    /**
     * 아이디(필수)
     */
    @NotBlank(message = "userId는 필수 입니다.")
    @Size(max = 20)
    private String userId;

    /**
     * 비밀번호
     */
    @NotBlank(message = "password 필수 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    /**
     * 단말기 고유번호
     */
    @NotBlank(message = "deviceId 필수 입니다.")
    private String deviceId;

    /**
     * 단말기 OS타입(1:ios, 2:android, 9:etc)
     */
    @Size(max = 1)
    @NotBlank(message = "osType 필수 입니다.")
    private String osType;

    /**
     * 단말기 OS버전
     */
    @NotBlank(message = "osVer 필수 입니다.")
    private String osVer;

    /**
     * 로그인 타입(M:사용자입력, A:자동로그인)
     */
    @Size(max = 1)
    @NotBlank(message = "loginType 필수 입니다.")
    private String loginType;

}
