package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
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

}