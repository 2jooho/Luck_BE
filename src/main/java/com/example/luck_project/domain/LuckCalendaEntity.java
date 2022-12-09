package com.example.luck_project.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "calenda_data")
public class LuckCalendaEntity {

    /** 순번 */
    @Id
    @Column(name = "cd_no")
    private Integer cdNo;

    /**
     * ??
     */
    @Column(name = "cd_sgi")
    private Integer cdSgi;

    /**
     * 양력 년
     */
    @Column(name = "cd_sy")
    private String cdSy;

    /**
     * 양력 월
     */
    @Column(name = "cd_sm")
    private String cdSm;

    /**
     * 양력 일
     */
    @Column(name = "cd_sd")
    private String cdSd;

    /**
     * 음력 년
     */
    @Column(name = "cd_ly")
    private String cdLy;
    /**
     * 음력 월
     */
    @Column(name = "cd_lm")
    private String cdLm;
    /**
     * 음력 일
     */
    @Column(name = "cd_ld")
    private String cdLd;
    /**
     * 한자년지
     */
    @Column(name = "cd_hyganjee")
    private String cdHyganjee;
    /**
     * 한글년지
     */
    @Column(name = "cd_kyganjee")
    private String cdKyganjee;

    /**
     * 한자일지
     */
    @Column(name = "cd_hdganjee")
    private String cdHdganjee;

    /**
     * 한글일지
     */
    @Column(name = "cd_kdganjee")
    private String cdKdganjee;

}












