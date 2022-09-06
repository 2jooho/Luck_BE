package com.example.luck_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 유저 사주 정보 엔티티
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MyFortune_info_entity")
public class MyFortuneInfoEntity {

    /**
     * 사용자 아이디
     */
    @Id
    @Column(name = "user_Id")
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

}
