package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLuckInfoDto {
    /** 한글 년간 */
    private String koYearTop;

    /** 한자 년간 */
    private String chYearTop;

    /** 년간 이미지 */
    private String yearTopImg;

    /** 한글 월간 */
    private String koMonthTop;

    /** 한자 월간 */
    private String chMonthTop;

    /** 월간 이미지 */
    private String monthTopImg;

    /** 한글 일간 */
    private String koDayTop;

    /** 한자 일간 */
    private String chDayTop;

    /** 일간 이미지 */
    private String dayTopImg;

    /** 한글 시간 */
    private String koTimeTop;

    /** 한자 시간 */
    private String chTimeTop;

    /** 시간 이미지 */
    private String timeTopImg;

    /** 한글 년지 */
    private String koYearBtm;

    /** 한자 년지 */
    private String chYearBtm;

    /** 년지 이미지 */
    private String yearBtmImg;

    /** 한글 월지 */
    private String koMonthBtm;

    /** 한자 월지 */
    private String chMonthBtm;

    /** 월지 이미지 */
    private String monthBtmImg;

    /** 한글 일지 */
    private String koDayBtm;

    /** 한자 일지 */
    private String chDayBtm;

    /** 일지 이미지 */
    private String dayBtmImg;

    /** 한글 시지 */
    private String koTimeBtm;

    /** 한자 시지 */
    private String chTimeBtm;

    /** 시지 이미지 */
    private String timeBtmImg;
}
