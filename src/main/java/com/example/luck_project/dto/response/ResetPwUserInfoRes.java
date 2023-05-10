package com.example.luck_project.dto.response;

import com.example.luck_project.domain.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResetPwUserInfoRes {
    /** 아이디 */
    private String userId;

    /** 이름 */
    private String name;

    /** 생년월일 */
    private String birth;

    /**
     * 비밀번호 재설정 응답설정
     * @return
     */
    public void entityToResetPwUserInfoRes(UserEntity userEntity){
        setUserId(userEntity.getUserId());
        setName(userEntity.getUsername());
        setBirth(userEntity.getBirth());
    }
}
