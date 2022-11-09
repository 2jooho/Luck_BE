package com.example.luck_project.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_login_history")
public class UserLoginHistoryEntity {
    /** 히스토리 시퀀스 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LLH_ID")
    private long llhId;

    /** 아이디 */
    @Column(name = "USER_ID")
    private String userId;

    /** 로그인 일시 */
    @Column(name = "LOGIN_DATE")
    private String loginDate;

    /** 로그인 상태코드 */
    @Column(name = "LOGIN_ISCODE")
    private String loginIscode;

    /** 메타 정보 */
    @Column(name = "LOGIN_META")
    private String loginMeta;


}