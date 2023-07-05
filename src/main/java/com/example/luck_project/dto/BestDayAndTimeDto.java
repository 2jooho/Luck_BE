package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestDayAndTimeDto {
    /** 띠 */
    private String versYear;

    /** 날짜 */
    private String bestDate;

    /** 시간 */
    private String bestTime;

    /** 띠 이미지 url */
    private String versYearImgUrl;

    public void of(String versYear, String bestDate, String bestTime, String versYearImgUrl){
        this.versYear = versYear;
        this.bestDate = bestDate.substring(0,4) + ". " + bestDate.substring(4,6) + ". " + bestDate.substring(6,8);
        this.bestTime = bestTime.substring(0,2) + " ~ " + bestTime.substring(6,8);
        this.versYearImgUrl = versYearImgUrl;
    }

}