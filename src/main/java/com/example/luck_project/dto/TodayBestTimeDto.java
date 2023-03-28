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

}