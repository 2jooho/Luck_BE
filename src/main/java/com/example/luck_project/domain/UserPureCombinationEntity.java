package com.example.luck_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 사용자의 비장술 조합 (combination)
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_user_pure_luck_comb")
@DynamicUpdate
public class UserPureCombinationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사주조합
     */
    @Column(name = "LUCK_CNCTN")
    private String luckCnctn;

    /**
     * 띠
     */
    @Column(name = "VERS_YEAR")
    private String versYear;

    /**
     * 비장술 년지
     */
    @Column(name = "PURE_YEAR")
    private String pureYear;

    /**
     * 비장술 일지
     */
    @Column(name = "PURE_DAY")
    private String pureDay;

    public void of(String luckCnctn, String versYear, String pureYear, String pureDay) {
        this.luckCnctn = luckCnctn;
        this.versYear = versYear;
        this.pureYear = pureYear;
        this.pureDay = pureDay;
    }
}