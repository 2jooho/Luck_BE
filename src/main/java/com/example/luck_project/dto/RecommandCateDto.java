package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommandCateDto {
    /** 카테고리 코드 */
    private String cateCode;

    /** 카테고리 이름 */
    private String cateNmae;

    /** 카테고리 이미지 url */
    private String cateImgUrl;

    /** 카테고리 내용 */
    private String cateCntnt;

    /** 순위 */
    private String rank;

}
