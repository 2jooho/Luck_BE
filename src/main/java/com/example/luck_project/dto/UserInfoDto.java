package com.example.luck_project.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    /** 아이디 */
    private String userId;

    /** 닉네임 */
    private String nickName;

    /** 생년월일 */
    private String birth;

    /** 생일구분 */
    private String birthFlag;

    /** 년지 */
    private String yearBtm;

    /** 일지 */
    private String dayBtm;

    /** 관심 카테고리 목록 */
    private String cateCodeList;

}
