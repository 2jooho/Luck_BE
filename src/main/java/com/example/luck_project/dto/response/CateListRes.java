package com.example.luck_project.dto.response;

import com.example.luck_project.dto.CateDetailDto;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CateListRes {

    /** 카테고리 코드 */
    private String cateCode;

    /** 현재 페이지 */
    private Integer page;

    /** 열람 가능 개수 */
    private Integer rdnAvlblCnt;

    /** 열람 제한 여부 */
    private String rdnRstrcYn;

    /** 결제 시작 일시(열람 제한 여부 'N'인 경우 응답) */
    private String paymentStartDate;

    /** 결제 종료 일시(열람 제한 여부 'N'인 경우 응답) */
    private String paymentEndDate;

    /** 상세 카테고리 리스트 */
    private List<CateDetailDto> cateDetailList;

}
