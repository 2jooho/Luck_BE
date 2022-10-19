package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CateDetailDto {

    /** 상세 카테고리 코드 */
    private String cateDetlCode;

    /** 상세 카테고리 명 */
    private String cateDetlName;

    /** 상세 카테고리 내용 */
    private String cateCntnt;

    /** 상세 카테고리 이미지 URL */
    private String cateImgUrl;

}
