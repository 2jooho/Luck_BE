package com.example.luck_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_user_info")
public class UserEntity {

    /** 아이디 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private String userId;

    /** 이름 */
    @Column(name = "USER_NAME")
    private String userName;

    /** 닉네임 */
    @Column(name = "NICK_NAME")
    private String nickName;

    /** 생년월일 */
    @Column(name = "BIRTH")
    private String birth;

    /** 생일구분 */
    @Column(name = "BIRTH_FLAG")
    private String birthFlag;

    /** 태어난 시간 */
    @Column(name = "BIRTH_TIME")
    private String birthTime;

    /** 관심 카테고리 코드 리스트 (구분자 ',' 나열) */
    @Column(name = "CATE_CODE_LIST")
    private String cateCodeList;

//    @JoinColumn(name = "USER_ID")
//    @OneToOne(fetch = FetchType.LAZY)
//    private UserLuckInfoEntity userLuckInfoEntity;

}


