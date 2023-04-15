package com.music.common.exception;

public class ApplicationException extends RuntimeException {

    private Integer code;

    private String error;


    public ApplicationException(Integer code, String message) {
        super(message);
        this.code = code;
        this.error = message;
    }


    public Integer getCode() {
        return code;
    }

    public String getError() {
        return error;
    }


    /**
     * avoid the expensive and useless stack trace for api exceptions
     *
     * @see Throwable#fillInStackTrace()
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}