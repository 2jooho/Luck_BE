package com.example.luck_project.batch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class RequestPushMessage {
    /** 제목 */
    String title;
    /** 내용 */
    String body;
    /** 데이터 */
    Map<String, String> data;
    /** 이미지 주소 */
    String image;
    /** 대상 사용자 번호 */
    List<Long> userNos;

    @Builder
    public RequestPushMessage(String title, String body, Map<String, String> data, String image, List<Long> userNos) {
        this.title = title;
        this.body = body;
        this.data = data;
        this.image = image;
        this.userNos = userNos;
    }


}