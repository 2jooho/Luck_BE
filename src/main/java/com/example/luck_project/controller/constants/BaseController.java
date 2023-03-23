package com.example.luck_project.controller.constants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import static com.example.luck_project.constants.StaticValues.RESULT_CODE;
import static com.example.luck_project.constants.StaticValues.RESULT_MESSAGE;
import static com.example.luck_project.constants.ResponseCode.SUCCESS;

@Slf4j
public class BaseController {
    protected HttpHeaders getSuccessHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(RESULT_CODE, SUCCESS.getResponseCode());
        headers.set(RESULT_MESSAGE, SUCCESS.getUrlEncodingMessage());
        return headers;
    }

}
