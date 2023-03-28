package com.example.luck_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_cate")
public class CateEntity {
    /** 카테고리 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATE_CD")
    private String cateCode;

    /** 카테고리 이름 */
    @Column(name = "CATE_NAME")
    private String cateName;

    /** 순번 */
    @Column(name = "ORDER_NO")
    private String orderNo;

    /** 사용여부 */
    @Column(name = "USE_YN")
    private String useYn;


//    @JoinColumn(name = "CATE_CD")
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<CateImgEntity> cateImgEntity;

}