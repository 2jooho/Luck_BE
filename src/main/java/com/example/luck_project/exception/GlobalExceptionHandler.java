package com.example.luck_project.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.luck_project.exception.ErrorHttpStatusMapper.mapToStatus;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException ce){
        final ErrorCode errorCode = ce.getErrorCode();
        log.error("customError: {}", errorCode);
        return new ResponseEntity<>(new ErrorResponse(errorCode, errorCode.getMessage()),
                mapToStatus(errorCode));
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(final RuntimeException re) {
        log.error("handleRuntimeException : {}", re.getMessage());
        return re.getMessage();
    }

    @Data
    public static class ErrorResponse {
        private final ErrorCode errorCode;
        private final String errorMessage;
    }

}
