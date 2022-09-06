package com.example.luck_project.exception;

import org.springframework.http.HttpStatus;

public class ErrorHttpStatusMapper {
    public static HttpStatus mapToStatus(ErrorCode errorCode) {
        switch (errorCode) {
            case ALREADY_EXISTS_USER:
            case VALIDATION_FAIL:
            case BAD_REQUEST:
            case EVENT_CREATE_OVERLAPPED_PERIOD:
                return HttpStatus.BAD_REQUEST;
            case PASSWORD_NOT_MATCH:
            case USER_NOT_FOUND:
                return HttpStatus.UNAUTHORIZED;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
