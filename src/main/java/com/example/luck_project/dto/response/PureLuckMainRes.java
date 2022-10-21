package com.example.luck_project.dto.response;

import com.example.luck_project.dto.BestDayAndTimeDto;
import com.example.luck_project.dto.MessageDto;
import com.example.luck_project.dto.TodayBestTimeDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PureLuckMainRes {

    /** 오늘의 띠 */
    private String todayVersYear;

    /** 최고 날짜 및 시간 목록 */
    private List<BestDayAndTimeDto> bestDayAndTimeList;

    /** 당일 최적의 시간 목록 */
    private List<TodayBestTimeDto> todayBestTimeList;

    /** 메시지 목록 */
    private List<MessageDto> messageList;

}
