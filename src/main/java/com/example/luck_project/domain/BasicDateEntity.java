package com.example.luck_project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lck_basic_date")
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
public class BasicDateEntity implements Persistable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 기준날짜 */
    @Column(name = "BASIC_DATE")
    private String basicDate;

    /** 띠 */
    @Column(name = "VERS_YEAR_INFO")
    private String versYearInfo;

    /** 등록일시 */
    @CreatedDate
    @Column(name = "RGSTT_DTM")
    private LocalDateTime rgsttDtm;

    /** 등록자 */
    @Column(name = "RGPS_ID")
    private String rgpsId;

    @Builder
    public BasicDateEntity(String basicDate, String versYearInfo) {
        this.basicDate = basicDate;
        this.versYearInfo = versYearInfo;
    }

    @Override
    public String getId() {
        return basicDate;
    }

    @Override
    public boolean isNew() {
        return rgsttDtm == null;
    }
}