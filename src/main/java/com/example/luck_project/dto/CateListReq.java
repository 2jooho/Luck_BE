package com.example.luck_project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@ToString
@Valid
public class CateListReq {
    /**
     * 카테고리 코드(필수)
     */
    @NotBlank(message = "cateCode는 필수 입니다.")
    private String cateCode;

    /**
     * 페이징 사이즈
     */
    private Integer pageingSize;

    /**
     * 페이지
     */
    private Integer page;

}