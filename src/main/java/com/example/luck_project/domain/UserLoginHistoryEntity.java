package com.example.luck_project.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "lck_login_history")
@Builder
@SequenceGenerator(
        name = "SEQ_USER_LOGIN_HISTORY"
        , sequenceName = "LOGIN_HISTORY_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
public class UserLoginHistoryEntity {

    /** 로그인 히스토리 시퀀스 */
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "SEQ_USER_LOGIN_HISTORY"
    )
    private Long llhId;

    /** 사용자 아이디 */
    @Column(name = "USER_ID", length = 20)
    private String userId;

    /** 아이피 */
    @Column(name = "LOGIN_IP", length = 20)
    private String loginIp;

    /** 메타정보(기종 및 버전) */
    @Column(name = "LOGIN_META", length = 800)
    private String loginMeta;

    /** 페이지 경로 정보 */
    @Column(name = "LOGIN_REFFER", length = 800)
    private String loginReffer;

    /** 로그인 일시 */
    @Column(name = "LOGIN_DATE")
    private LocalDateTime loginDate;

    /** 상태코드(X:로그인실패, Y:로그인성공, I:로그인 시도 횟수 초기화) */
    @Column(name = "LOGIN_ISCODE", length = 1)
    private String loginIscode;


    public void iscodeUpdate(String iscode){
        this.loginIscode = iscode;
    }

}
