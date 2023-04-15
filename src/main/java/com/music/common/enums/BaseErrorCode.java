package com.music.common.enums;


import com.music.common.exception.ApplicationExceptionEnum;

public enum BaseErrorCode implements ApplicationExceptionEnum {

    SUCCESS(200,"success"),
    SYSTEM_ERROR(90000,"服务器内部错误,请联系管理员"),
    PARAMETER_ERROR(90001,"参数校验错误"),


            ;

    private Integer code;
    private String error;

    BaseErrorCode(Integer code, String error){
        this.code = code;
        this.error = error;
    }
    public Integer getCode() {
        return this.code;
    }

    public String getError() {
        return this.error;
    }

}