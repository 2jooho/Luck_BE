package com.example.luck_project.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@Valid
public class ConfirmsSMS {
    @NotBlank(message = "핸드폰번호는 필수 입력값입니다.")
    @Pattern(regexp = "^[0-9]{0,12}$" , message = "핸드폰번호는 12자리 숫자 형식입니다.")
    private String phoneNm;
    @NotBlank(message = "인증번호는 필수 입력값입니다.")
    @Pattern(regexp = "^[0-9]{0,4}$" , message = "인증번호는 4자리 숫자 형식입니다.")
    private String authNm;

}
