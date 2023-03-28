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
    @Column(name = "USER_ID")
    private String userId;

    /** 년간 */
    @Column(name = "YEAR_TOP")
    private String yearTop;

    /** 년지 */
    @Column(name = "YEAR_BTM")
    private String yearBtm;

    /** 일간 */
    @Column(name = "DAY_TOP")
    private String dayTop;

    /** 일지 */
    @Column(name = "DAY_BTM")
    private String dayBtm;

    @Builder
    public void of(String userId, String yearTop, String yearBtm, String dayTop, String dayBtm) {
        this.userId = userId;
        this.yearTop = yearTop;
        this.yearBtm = yearBtm;
        this.dayTop = dayTop;
        this.dayBtm = dayBtm;
    }
}
