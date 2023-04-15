package com.music.common.enums;

import com.music.common.exception.ApplicationExceptionEnum;

public enum CommonErrorCode implements ApplicationExceptionEnum {
    NOT_LOGIN(500, "用户名或密码错误");

    private final Integer code;
    private final String error;

    CommonErrorCode(Integer code, String error) {
        this.code = code;
        this.error = error;
    }
    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getError() {
        return this.error;
    }
}
