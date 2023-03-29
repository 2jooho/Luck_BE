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
@Table(name = "lck_cate_img")
public class CateImgEntity {
    /** 카테고리 코드 */
    @Id
    @Column(name = "CATE_CD")
    private String cateCode;

    /** 이미지 경로 */
    @Column(name = "FILE_PATH")
    private String filePath;

    /** 이미지 명 */
    @Column(name = "FILE_NAME")
    private String fileName;

    /** 이미지 타입(1:기본, 2:상세) */
    @Column(name = "IMAGE_TYPE")
    private String imageType;

    /** 이미지 순번 */
    @Column(name = "IMAGE_ORDER")
    private String imageoOrder;

}
