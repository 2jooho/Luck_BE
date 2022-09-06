package com.example.luck_project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MainRes {
    /**
     * 사용자 아이디
     */
    private String userId;

    /**
     * 사용자 운 점수
     */
    private String todayLuckLv;

    /**
     * 오늘 운 글귀
     */
    private String todayLuckText;

}
