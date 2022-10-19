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
@Table(name = "lck_cate_detl")
public class CateDetailEntity {

    /** 카테고리 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATE_CD")
    private String cateCd;

    /** 카테고리 상세 코드 */
    @Column(name = "CATE_DETL_CD")
    private String cateDetlCd;

    /** 카테고리 상세 명 */
    @Column(name = "CATE_DETL_NAME")
    private String cateDetlName;

    /** 카테고리 상세 내용 */
    @Column(name = "CATE_DETL_CNTNT")
    private String cateDetlCntnt;

    /** 사용여부 */
    @Column(name = "USE_YN")
    private String useYn;

    /** 광고여부 */
    @Column(name = "AD_YN")
    private String adYn;

    /** 조회수 */
    @Column(name = "INQRY_CNT")
    private Integer inqryCnt;

    /** 순번 */
    @Column(name = "ORDER_NO")
    private Integer orderNo;

}
