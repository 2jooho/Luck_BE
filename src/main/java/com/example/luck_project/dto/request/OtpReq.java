package com.example.luck_project.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@ToString
@Valid
public class OtpReq {

    /**
     * 단말기 OS타입(1:ios, 2:android, 9:etc)
     */
    @Size(max = 1)
    @Pattern(regexp = "(?=.*[0-9])", message = "osType이 숫자 타입이 아닙니다. {osType is not number}")
    @NotBlank(message = "osType 필수 입니다.")
    private String osType;

}
