package com.example.luck_project.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@Valid
public class FindIdReq {
    /**
     * 이름
     */
    @NotBlank(message = "name은 필수 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,20}$" , message = "이름은 특수문자를 포함하지 않은 2~20자리여야 합니다.")
    private String name;

    /**
     * 핸드폰 번호
     */
    @NotBlank(message = "phoneNm는 필수 입니다.")
    @Pattern(regexp = "^[0-9]{0,12}$" , message = "핸드폰번호는 12자리 숫자 형식입니다.")
    private String phoneNm;

    /**
     * 생년월일
     */
    @NotBlank(message = "brith는 필수 입니다.")
    @Pattern(regexp = "^[0-9]{8}$" , message = "생년월일은 8자리 숫자입니다.")
    private String brith;
}
