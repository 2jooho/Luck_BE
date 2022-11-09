package com.example.luck_project.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdCheckRes {
    /** 아이디 */
    private String userId;

    /** 아이디 중복 여부 */
    private String idDplctYn;
    
}