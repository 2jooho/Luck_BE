package com.example.luck_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 *
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "today_luck_info")
public class TodayLuckEntity {
    /**
     * 사용자 아이디
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String userId;

    /**
     * 년지 한글
     */
    @Column(name = "year_luck_kr")
    private String yearLuckKr;

    /**
     * 일지 한글
     */
    @Column(name = "day_luck_kr")
    private String dayLuckKr;

    /**
     * 운 레벨
     */
    @Column(name = "luck_level")
    private String luckLv;

    /**
     * 운세 정보
     */
    @Column(name = "luck_text")
    private String luckText;

}
