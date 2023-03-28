package com.example.luck_project.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JoinRes {
    /** 아이디 */
    private String userId;

    /** 닉네임 */
    private String nickName;

}
