package com.example.luck_project.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "lck_user_luck_info")
public class UserLuckInfoEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 아이디 */
    @Column(name = "user_id", unique = true)
    private String userId;

    /** 한글 년간 */
    @Column(name = "ko_year_top", nullable = false)
    private String koYearTop;

    /** 한글 년지 */
    @Column(name = "ko_year_btm", nullable = false)
    private String koYearBtm;

    /** 한자 년간 */
    @Column(name = "ch_year_top", nullable = false)
    private String chYearTop;

    /** 한자 년지 */
    @Column(name = "ch_year_btm", nullable = false)
    private String chYearBtm;

    /** 한글 일간 */
    @Column(name = "ko_day_top", nullable = false)
    private String koDayTop;

    /** 한글 일지 */
    @Column(name = "ko_day_btm", nullable = false)
    private String koDayBtm;

    /** 한자 일간 */
    @Column(name = "ch_day_top", nullable = false)
    private String chDayTop;

    /** 한자 일지 */
    @Column(name = "ch_day_btm", nullable = false)
    private String chDayBtm;

    /** 한글 월지 */
    @Column(name = "ko_month_top", nullable = false)
    private String koMonthTop;

    /** 한글 월간 */
    @Column(name = "ko_month_btm", nullable = false)
    private String koMonthBtm;

    /** 한자 월간 */
    @Column(name = "ch_month_top", nullable = false)
    private String chMonthTop;

    /** 한자 월지 */
    @Column(name = "ch_month_btm", nullable = false)
    private String chMonthBtm;

    /** 한글 시간 */
    @Column(name = "ko_time_top", nullable = true)
    private String koTimeTop;

    /** 한글 시지 */
    @Column(name = "ko_time_btm", nullable = true)
    private String koTimeBtm;

    /** 한자 시간 */
    @Column(name = "ch_time_top", nullable = true)
    private String chTimeTop;

    /** 한자 시지 */
    @Column(name = "ch_time_btm", nullable = true)
    private String chTimeBtm;

    @Builder
    public void koOf(String userId, String koYearTop, String koYearBtm, String koDayTop, String koDayBtm, String koMonthTop, String koMonthBtm, String koTimeTop, String koTimeBtm) {
        this.userId = userId;
        this.koYearTop = koYearTop;
        this.koYearBtm = koYearBtm;
        this.koDayTop = koDayTop;
        this.koDayBtm = koDayBtm;
        this.koMonthTop = koMonthTop;
        this.koMonthBtm = koMonthBtm;
        this.koTimeTop = koTimeTop;
        this.koTimeBtm = koTimeBtm;
    }

    @Builder
    public void chOf(String chYearTop, String chYearBtm, String chDayTop, String chDayBtm, String chMonthTop, String chMonthBtm, String chTimeTop, String chTimeBtm) {
        this.chYearTop = chYearTop;
        this.chYearBtm = chYearBtm;
        this.chDayTop = chDayTop;
        this.chDayBtm = chDayBtm;
        this.chMonthTop = chMonthTop;
        this.chMonthBtm = chMonthBtm;
        this.chTimeTop = chTimeTop;
        this.chTimeBtm = chTimeBtm;
    }
}
