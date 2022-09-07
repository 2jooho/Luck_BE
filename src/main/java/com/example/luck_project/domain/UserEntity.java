package com.example.luck_project.domain;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserEntity {
    /**
     * 사용자 아이디
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String userId;

    /**
     * 사용자 이름
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 생년월일
     */
    @Column(name = "birth_date")
    private String birthDate;

    /**
     * 태어난 시/분
     */
    @Column(name = "birth_date_time")
    private String birthDateTime;

    @JoinColumn(name = "userId")
    @OneToOne(fetch = FetchType.LAZY)
    private MyFortuneInfoEntity myFortuneInfoEntity;

}
