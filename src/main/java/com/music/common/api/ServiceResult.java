package com.music.common.api;

import com.music.common.exception.ApplicationExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceResult<T> implements Serializable {

    private boolean success = false;

    private Integer code;

    private String message;

    private String type;


    private T data;


    public static <T> ServiceResult<T> success(String message) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.code = 200;
        item.message = message;
        item.type = "success";
        return item;
    }
    public static <T> ServiceResult<T> success(String message, T result) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.code = 200;
        item.message = message;
        item.type = "success";
        item.data = result;
        return item;
    }

    public static <T> ServiceResult<T> failure(String errorMessage) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = 500;
        item.message = errorMessage;
        item.type = "error";
        return item;
    }

    public static <T> ServiceResult<T> failure(Integer errorCode, String errorMessage) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = errorCode;
        item.message = errorMessage;
        item.type = "error";
        return item;
    }


    public static ServiceResult failure(ApplicationExceptionEnum applicationExceptionEnum) {
        return failure(applicationExceptionEnum.getCode(), applicationExceptionEnum.getError());
    }




}
