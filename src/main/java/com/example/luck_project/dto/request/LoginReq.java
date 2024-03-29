package com.example.luck_project.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Valid
public class LoginReq {
    /**
     * 아이디(필수)
     */
    @NotBlank(message = "userId는 필수 입니다.")
    @Size(max = 20)
    @ApiModelProperty(name="userId", example = "test", value = "사용자 아이디", required = true) //swagger 처리
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
    @Pattern(regexp = "^[129]{1}$", message = "단말기 OS 타입은 1,2,9만 가능합니다.")
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
    @Pattern(regexp = "^[MAma]{1}$", message = "로그인 타입은 M, A만 가능합니다.")
    private String loginType;

    /**
     * 로그인 방법(B:기본 로그인, K:카카오 로그인, G:구글 로그인)
     */
    @Size(max = 1)
    @NotBlank(message = "loginDvsn 필수 입니다.")
    @Pattern(regexp = "^[BKGbkg]{1}$", message = "로그인 타입은 B, K ,G만 가능합니다.")
    private String loginDvsn;

}
