package com.example.luck_project.dto.response;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.MyLuckBtmDto;
import com.example.luck_project.dto.MyLuckTopDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyPageRes {
    /** 아이디 */
    private String userId;

    /** 생년월일 */
    private String birth;

    /** 나의 운세 날짜 */
    private String myLuckData;

    /** 나의 운세 천간 */
    private MyLuckTopDto myLuckTopDto;

    /** 나의 운세 지지 */
    private MyLuckBtmDto myLuckBtmDto;

    /** 나의 추천인 코드 */
    private String recomendCode;

    @Builder
    public void of(String userId, String birth, MyLuckTopDto myLuckTopDto, MyLuckBtmDto myLuckBtmDto, String recomendCode, Boolean isTime){
        this.userId = userId;
        this.birth = birth;
        this.myLuckTopDto = myLuckTopDto;
        this.myLuckBtmDto = myLuckBtmDto;
        this.recomendCode = recomendCode;
        this.myLuckData = myLuckTopDto.getYearLuckKorean() + myLuckBtmDto.getYearLuckKorean() + "년"
                + myLuckTopDto.getMonthLuckKorean() + myLuckBtmDto.getMonthLuckKorean() + "월"
                + myLuckTopDto.getDayLuckKorean() + myLuckBtmDto.getDayLuckKorean() + "일";

        //생시 존재하는경우
        if(isTime){
            this.myLuckData = this.myLuckData + myLuckTopDto.getTimeLuckKorean() + myLuckBtmDto.getTimeLuckKorean() + "생";
        }
    }

}
