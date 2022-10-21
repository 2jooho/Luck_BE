package com.example.luck_project.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@ToString
@Valid
public class PureLuckMainReq {
    /**
     * 아이디(필수)
     */
    @NotBlank(message = "userId는필수 입니다.")
    private String userId;

    /**
     * 비장술 조합(필수)
     */
    @NotBlank(message = "pureCnctn는 필수 입니다.")
    private String pureCnctn;

    /**
     * 상세 카테고리 코드(필수)
     */
    @NotBlank(message = "cateDetailCode는 필수 입니다.")
    private String cateDetailCode;

    /**
     * 열람 가능 개수
     */
    @NotBlank(message = "rdnAvlblCnt 필수 입니다.")
    private Integer rdnAvlblCnt;

    /**
     * 열람 제한 여부
     */
    @NotBlank(message = "rdnRstrcYn 필수 입니다.")
    private String rdnRstrcYn;

}