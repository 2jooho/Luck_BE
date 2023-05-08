package com.example.luck_project.dto.response;

import com.example.luck_project.domain.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MyPageRes {
    /** 아이디 */
    private String userId;

    /** 생년월일 */
    private String birth;

    /** 나의 지지(한글)  */
    private String luckBtmKoreanText;

    /** 나의 운세 목록 */
    private List<MyLuckList> myLuckList;

    /** 나의 추천인 코드 */
    private String recomendCode;

}
