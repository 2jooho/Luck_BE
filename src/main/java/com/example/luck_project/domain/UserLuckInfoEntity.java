package com.example.luck_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "lck_user_luck_info")
public class UserLuckInfoEntity {

    /** 아이디 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
