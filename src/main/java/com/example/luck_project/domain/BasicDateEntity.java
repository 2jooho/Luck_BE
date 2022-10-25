package com.example.luck_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_basic_date")
@DynamicUpdate
public class BasicDateEntity {

    /** 기준날짜 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basic_date")
    private String basicDate;

    /** 띠 */
    @Column(name = "VERS_YEAR_INFO")
    private String versYearInfo;

}
