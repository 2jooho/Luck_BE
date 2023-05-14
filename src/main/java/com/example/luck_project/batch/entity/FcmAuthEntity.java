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
@Table(name = "lck_fcm_auth_info")
public class FcmAuthEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fcm_topic_code", length = 20, nullable = false)
    private String topicCode; //토픽코드

}
