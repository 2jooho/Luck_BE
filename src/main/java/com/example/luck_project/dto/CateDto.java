package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CateDto {
    /** 카테고리 코드 */
    private String cateCode;

    /** 카테고리 이름 */
    private String cateName;

    /** 카테고리 이미지 목록 */
    private CateImgDto cateImgDto;

}
