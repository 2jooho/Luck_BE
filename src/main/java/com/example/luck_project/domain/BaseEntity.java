package com.example.luck_project.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    /** 등록일시 */
    @CreatedDate
    @Column(name = "RGSTT_DTM")
    private LocalDateTime rgsttDtm;

    /** 등록자 */
    @Column(name = "RGPS_ID")
    private String rgpsId = "API";

    /** 수정일시 */
    @CreatedDate
    @Column(name = "EDIT_DTM")
    private LocalDateTime editDtm;

    /** 수정자 */
    @Column(name = "UPUS_ID", length = 20)
    private String upusId;
}