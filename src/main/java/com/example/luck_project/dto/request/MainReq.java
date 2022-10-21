package com.example.luck_project.dto.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
@Getter
@ToString
@Valid
public class MainReq {
    /**
     * 사용자 아이디(필수)
     */
    @NotBlank(message = "userId는 필수 입니다.")
    private String userId;

}
