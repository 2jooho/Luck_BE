package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CateImgDto {
    /** 이미지 url*/
    private String imgUrl;

    /** 이미지 타입(1:기본, 2:상세) */
    private String imgType;

}
