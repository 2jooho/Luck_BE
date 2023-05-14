package com.example.luck_project.batch.entity;

import com.example.luck_project.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_push_msg")
public class PushMessageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //메시지 id

    @Column(name = "time_luck", length = 10, nullable = false)
    private String timeLuck; //시간 운

    @Column(name = "msg_lrcl_group_code", length = 10, nullable = false)
    private String msgLGoupCode; //메시지 대분류 그룹(ex. FCM)

    @Column(name = "msg_smcl_group_code", length = 20, nullable = false)
    private String msgSGroupCode; //메시지 소분류 그룹

    @Column(name = "title_msg", length = 500)
    private String titleMsg; //타이틀

    @Column(name = "main_msg", length = 1000)
    private String mainMsg; //메인 메시지

    @Column(name = "sub_msg", length = 4000)
    private String subMsg; //서브 메시지

}
