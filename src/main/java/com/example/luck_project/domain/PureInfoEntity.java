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
@Table(name = "lck_pure_info")
public class PureInfoEntity {

    /** 비장술 조합 */
    @Id
    @Column(name = "PURE_CNCTN")
    private String pureCnctn;

    /** 비장술 운세 내용 */
    @Column(name = "PURE_CNTNT")
    private String pureCntnt;

    /** 캐릭터 구분(1:까복이, 2:까순이) */
    @Column(name = "CHARACTOR_FLAG")
    private String charactorFlag;

}
