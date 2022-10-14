package com.example.luck_project.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MainRes {
    /** 닉네임 */
    private String nickName;

    /** 비장술 조합 */
    private String pureCnctn;

    /** 비장술 내용 */
    private String pureCntnt;

    /** 캐릭터 구분(1:까복이, 2:까순이) */
    private String charactorFlag;

    /** 추천 카테고리 리스트 */
    private List<RecommandCateDto> recommandCateList;

    /** 카테고리 리스트 */
    private List<CateDto> cateDtoList;

}