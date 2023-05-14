package com.example.luck_project.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "lck_time_luck_symbol", indexes = @Index(name = "IDX_LCK_TIME_LUCK_SYMBOL_01", columnList = "ko_daytop, timetype"))
public class TimeLuckSymbolEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 한글 일간 */
    @Column(name = "ko_daytop", nullable = false, length = 10)
    private String koDayTop;

    /** 한자 일간 */
    @Column(name = "ch_daytop", length = 10)
    private String chDayTop;

    /** 시간 타입(1~12) */
    @Column(name = "timetype", nullable = false, length = 5)
    private String timeType;

    /** 한글 시주 */
    @Column(name = "ko_timejoo", nullable = false, length = 10)
    private String koTimeJoo;

    /** 한자 시주 */
    @Column(name = "ch_timejoo", length = 10)
    private String chTimeJoo;

}
