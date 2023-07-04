package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayBestTimeDto {

    /** 시간 띠 */
    private String timeVersYear;

    /** 시간 */
    private String bestTime;

    /** 시간 */
    private String timeVersYearImg;

    public void of(String timeVersYear, String bestTime, String timeVersYearImg){
        this.timeVersYear = timeVersYear;
        this.bestTime = bestTime.substring(0, 2) + " ~ " + bestTime.substring(6, 8);
        this.timeVersYearImg = timeVersYearImg;
    }

}