package com.example.luck_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    /** 메시지 아이디 */
    private String messageId;

    /** 메인 메시지 */
    private String message;

    /** 서브 메시지 */
    private String subMessage;

}