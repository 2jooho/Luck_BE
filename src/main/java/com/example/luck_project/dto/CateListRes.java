package com.example.luck_project.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CateListRes {

    /** 카테고리 코드 */
    private String cateCode;

    /** 현재 페이지 */
    private Integer page;

    /** 상세 카테고리 리스트 */
    private List<CateDetailDto> cateDetailList;

}
