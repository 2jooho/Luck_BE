package com.example.luck_project.dto.request;

import com.example.luck_project.domain.BaseEntity;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@ToString
@Valid
public class PureLuckMainReq extends BaseEntity {
    /**
     * 아이디(필수)
     */
    @NotBlank(message = "userId는필수 입니다.")
    private String userId;

    /**
     * 오늘 띠 정보(필수)
     */
    @NotBlank(message = "todayVersYear 필수 입니다.")
    private String todayVersYear;

    /**
     * 비장술 조합(필수)
     */
    @NotBlank(message = "pureCnctn는 필수 입니다.")
    private String pureCnctn;

    /**
     * 카테고리 코드(필수)
     */
    @NotBlank(message = "cateCode는 필수 입니다.")
    private String cateCode;

    /**
     * 상세 카테고리 코드(필수)
     */
    @NotBlank(message = "cateDetailCode는 필수 입니다.")
    private String cateDetailCode;

}
