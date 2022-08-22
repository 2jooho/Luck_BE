package com.luckC.core.domain.entity;

import org.gradle.api.tasks.InputDirectory;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class UserInfo{
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column
    private String name;

    @Column
    private int phone;

    @Column
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "birth_date")
    //출생년월일
    private String birthDate;

    @Column(name = "birth_time")
    //출생시간
    private String birthTime;

    @Column(name = "brthd_dvsn")
    //생일구분(음력/양력)
    private String brthdDvsn;

    @Column
    private String email;

    @Column(name ="daily_rdn_avlbl_tmcnt")
    //일일 열람 가능 횟수
    private String dailyRdnAvlblTmcnt;

    @Column(name = "daily_rdn_tmcnt")
    //일일 열람 횟수
    private String dailyRdnTmcnt;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserLuckInfo userLuckInfo;
}