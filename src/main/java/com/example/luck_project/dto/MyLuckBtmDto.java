package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyLuckBtmDto {
    /** 시지 이미지  */
    private String timeLuckImg;

    /** 시지 한글 */
    private String timeLuckKorean;

    /** 시지 한자 */
    private String timeLuckChinese;

    /** 일지 이미지 */
    private String dayLuckImg;

    /** 일지 한글 */
    private String dayLuckKorean;

    /** 일지 한자 */
    private String dayLuckChinese;

    /** 월지 이미지 */
    private String monthLuckImg;

    /** 월지 한글 */
    private String monthLuckKorean;

    /** 월지 한자 */
    private String monthLuckChinese;

    /** 년지 이미지 */
    private String yearLuckImg;

    /** 년지 한글 */
    private String yearLuckKorean;

    /** 년지 한자 */
    private String yearLuckChinese;
}
