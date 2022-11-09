package com.example.luck_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PASSWORD_NOT_MATCH(101,"비밀번호 불일치"),
    ALREADY_EXISTS_USER(102, "이미 있는 계정"),
    USER_NOT_FOUND(103, "존재하지 않는 계정"),
    VALIDATION_FAIL(104, "값이 유효하지 않음"),
    BAD_REQUEST(105, "잘못된 접근"),
    EVENT_CREATE_OVERLAPPED_PERIOD(106, "이벤트 기간 중복"),

    PURE_LUCK_INFO_FAIL(107, "비장술 운세 정보 미존재"),

    BASIC_CATE_INFO_FAIL(108, "기본 카테고리 정보 미존재"),

    CATE_DETAIL_INFO_FAIL(109, "상세 카테고리 정보 미존재"),

    USER_NOT_FOUND_PAYMENT(109, "결제 정보가 존재하지 않는 계정"),

    BASIC_DATE_NOT_FOUND(110, "기준 날짜 정보 미존재"),

    CATE_DETL_PURE_NOT_FOUND(111, "카테고리 상세 비장술 정보 미존재"),

    ALREADY_EXISTS_NICKNAME(112, "이미 존재하는 닉네임"),

    RE_TOKEN_RESPONSE(113, "토큰 재발급"),


    ;


    private final int status;
    private final String message;

}
