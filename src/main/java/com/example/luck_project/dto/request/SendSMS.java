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
public class SendSMS {
    @NotBlank(message = "핸드폰번호는 필수 입력값입니다.")
    @Pattern(regexp = "^[0-9]{0,11}$" , message = "핸드폰번호는 11자리 숫자 형식입니다.")
    private String phoneNm;

//    @NotBlank(message = "통신사는 필수 입력값입니다.")
//    @Pattern(regexp = "^[1234]{1}$" , message = "통신사 형식은 1,2,3,4 입니다.")
//    private String phoneType;
}
