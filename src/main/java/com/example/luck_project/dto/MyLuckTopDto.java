package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyLuckTopDto {
    /** 시간 이미지  */
    private String timeLuckImg;

    /** 시간 한글 */
    private String timeLuckKorean;

    /** 시간 한자 */
    private String timeLuckChinese;

    /** 일간 이미지 */
    private String dayLuckImg;

    /** 일간 한글 */
    private String dayLuckKorean;

    /** 일간 한자 */
    private String dayLuckChinese;

    /** 월간 이미지 */
    private String monthLuckImg;

    /** 월간 한글 */
    private String monthLuckKorean;

    /** 월간 한자 */
    private String monthLuckChinese;

    /** 년간 이미지 */
    private String yearLuckImg;

    /** 년간 한글 */
    private String yearLuckKorean;

    /** 년간 한자 */
    private String yearLuckChinese;
}
