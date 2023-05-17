package com.example.luck_project.dto.response;

import com.example.luck_project.dto.MyLuckBtmDto;
import com.example.luck_project.dto.MyLuckTopDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MyRecommandStarRes {
    /** 나의 추천별 개수 */
    private Integer myRecommandStarCnt;

    /** 추천별 상태 */
    private String recommandStarStatus;

    /** 열람가능 별 개수 */
    private Integer availableStarCnt;

    /** 열람완료 개수 */
    private Integer readingCompleteCnt;

    /** 사용가능 여부 */
    private String availableYn;


}
