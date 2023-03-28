package com.example.luck_project.dto.response;

import com.example.luck_project.dto.CateDto;
import com.example.luck_project.dto.RecommandCateDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRes {
    /** 사용자 아이디 */
    private String userId;

    /** 사용자 이름 */
    private String userName;

    /** 사용자 닉네임 */
    private String nickName;

    /** 비밀번호 갱신공지 여부 */
    private String passwdUpdateYn;

    /** 단말 고유번호 업데이트 여부 */
    private String deviceIdUpdateYn;

}
