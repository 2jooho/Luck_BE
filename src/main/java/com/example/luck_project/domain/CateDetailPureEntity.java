package com.example.luck_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 상세 카테고리 비장술 조합 정보
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_cate_detail_pure_info")
@DynamicUpdate
public class CateDetailPureEntity {

    /**
     * 비장술 타입
     */
    @Id
    @Column(name = "PURE_TYPE")
    private String pureType;

    /**
     * 상세 카테고리 코드
     */
    @Column(name = "CATE_DETL_CODE")
    private String cateDetlCode;

    /**
     * 비장술 순번
     */
    @Column(name = "PURE_ORDER")
    private String pureOrder;


}
