package com.sinsal.dto;

import com.sinsal.exception.ErrorCode;

public class ErrorResponse {
    private int status;
    private String error;
    private String code;
    private String message;

    public ErrorResponse (int status, String error, String message){
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().getReasonPhrase();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public int getStatus(){return status;}
    public String getError(){return error;}
    public String getCode(){return code;}
    public String getMessage(){return message;}
}
