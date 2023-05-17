package com.example.luck_project.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    INVALID_RESPONSE("9999", "요청이 처리 되지 않았습니다.",HttpStatus.INTERNAL_SERVER_ERROR),

    /** 공통 */
    SUCCESS("0000", "정상 처리되었습니다.",HttpStatus.OK),
    SERVER_ERROR("0099", "서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_PARAMETER("0003", "요청 파라미터의 값이 잘못되었습니다.",HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("0005", "잘못된 요청입니다.",HttpStatus.BAD_REQUEST),
    DB_ERROR("0008", "서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.",HttpStatus.INTERNAL_SERVER_ERROR),
    NO_DATA("0009", "조회된 데이터가 없습니다.",HttpStatus.NOT_FOUND),
    PASSWORD_NOT_MATCH("101","비밀번호 불일치", HttpStatus.INTERNAL_SERVER_ERROR),
    ALREADY_EXISTS_USER("102", "이미 있는 계정", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND("103", "존재하지 않는 계정", HttpStatus.NOT_FOUND),
    VALIDATION_FAIL("104", "값이 유효하지 않음", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("105", "잘못된 접근", HttpStatus.INTERNAL_SERVER_ERROR),
    EVENT_CREATE_OVERLAPPED_PERIOD("106", "이벤트 기간 중복", HttpStatus.INTERNAL_SERVER_ERROR),

    PURE_LUCK_INFO_FAIL("107", "비장술 운세 정보 미존재", HttpStatus.INTERNAL_SERVER_ERROR),

    BASIC_CATE_INFO_FAIL("108", "기본 카테고리 정보 미존재", HttpStatus.INTERNAL_SERVER_ERROR),

    CATE_DETAIL_INFO_FAIL("109", "상세 카테고리 정보 미존재", HttpStatus.INTERNAL_SERVER_ERROR),

    BASIC_DATE_NOT_FOUND("110", "기준 날짜 정보 미존재", HttpStatus.INTERNAL_SERVER_ERROR),

    CATE_DETL_PURE_NOT_FOUND("111", "카테고리 상세 비장술 정보 미존재", HttpStatus.INTERNAL_SERVER_ERROR),

    ALREADY_EXISTS_NICKNAME("112", "이미 존재하는 닉네임", HttpStatus.INTERNAL_SERVER_ERROR),

    RE_TOKEN_RESPONSE("113", "토큰 재발급", HttpStatus.INTERNAL_SERVER_ERROR),

    LOGIN_COUNT_FAIL("114", "로그인 횟수 초과", HttpStatus.INTERNAL_SERVER_ERROR),

    LOGIN_AUTH_FAIL("115", "12개월 이상 미접속 회원으로 휴면계정 처리 되었습니다. \n본인인증 후 이용 바랍니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    PAYMENT_INFO_NOT_FOUND("116", "사용자 결제 정보 조회 미존재", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_INFO_FOUND("117", "사용자 설정 정보 존재", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_LUCK_NOT_FOUND("118", "사용자 사주 정보 미존재", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_NOT_FOUND_PAYMENT("119", "결제 정보가 존재하지 않는 계정", HttpStatus.INTERNAL_SERVER_ERROR),
    PHONE_AUTH_NUMBER_FAIL("120", "인증번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    RESET_PASSWORD_MATCH_FAIL("121", "비밀번호와 비밀번호 확인이 다릅니다.", HttpStatus.BAD_REQUEST),
    RECOMMAND_CODE_NOT_FOUND("122", "추천인 코드 대상을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

    ;
    

    private final String responseCode;
    private final String message;
    private final HttpStatus httpStatus;

    //Map -> ImmutableMap 바꿔도 된다.
    private static final Map<String, ResponseCode> codes = Map.copyOf(
            Stream.of(values()).collect(Collectors.toMap(ResponseCode::getResponseCode, Function.identity())));

    public String getUrlEncodingMessage(){
        return URLEncoder.encode(this.message, StandardCharsets.UTF_8);
    }

    public static HttpStatus getHttpStatusFromResponseCode(String responseCode){
        if(codes.get(responseCode)!=null)
            return codes.get(responseCode).getHttpStatus();
        else
            return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
