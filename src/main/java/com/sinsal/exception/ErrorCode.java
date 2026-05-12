package com.sinsal.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    BIRTH_DATE_REQUIRED("SINSAL_001", "생년월일은 필수입니다.", HttpStatus.BAD_REQUEST),
    BIRTH_DATE_INVALID_FORMAT("SINSAL_002", "생년월일 형식이 올바르지 않습니다. (예: 2002-04-12)", HttpStatus.BAD_REQUEST),
    BIRTH_DATE_NOT_PAST("SINSAL_003", "생년월일은 과거 날짜여야 합니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("SINSAL_500", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
