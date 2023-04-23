package com.music.exception;

import com.music.common.api.ServiceResult;
import com.music.common.enums.BaseErrorCode;
import com.music.common.exception.ApplicationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


@RestControllerAdvice
@SuppressWarnings("unchecked")
public class GlobalExceptionHandler {


    /**
     *未知异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ServiceResult unknownException(Exception e) {
        e.printStackTrace();
        ServiceResult result = new ServiceResult();
        result.setCode(BaseErrorCode.SYSTEM_ERROR.getCode());
        result.setMessage(BaseErrorCode.SYSTEM_ERROR.getError());
        return result;
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object handleMethodArgumentNotValidException(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        ServiceResult resultBean = new ServiceResult();
        resultBean.setCode(BaseErrorCode.PARAMETER_ERROR.getCode());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
            // 读取参数字段，constraintViolation.getMessage() 读取验证注解中的message值
            String paramName = pathImpl.getLeafNode().getName();
            String message = "参数{".concat(paramName).concat("}").concat(constraintViolation.getMessage());
            resultBean.setMessage(message);
            return resultBean;
        }
        resultBean.setData(BaseErrorCode.PARAMETER_ERROR.getError() + ex.getMessage());
        return resultBean;
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public Object applicationExceptionHandler(ApplicationException e) {
        // 使用公共的结果类封装返回结果, 这里我指定状态码为
        ServiceResult resultBean =new ServiceResult();
        resultBean.setCode(e.getCode());
        resultBean.setMessage(e.getError());
        return resultBean;
    }

    /**
     * Validator 参数校验异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Object  handleException2(BindException ex) {
        FieldError err = ex.getFieldError();
        String message = "参数{".concat(err.getField()).concat("}").concat(err.getDefaultMessage());
        ServiceResult resultBean =new ServiceResult();
        resultBean.setCode(BaseErrorCode.PARAMETER_ERROR.getCode());
        resultBean.setMessage(message);
        return resultBean;


    }

    //json格式
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object  handleException1(MethodArgumentNotValidException ex) {
        StringBuilder errorMsg = new StringBuilder();
        BindingResult re = ex.getBindingResult();
        for (ObjectError error : re.getAllErrors()) {
            errorMsg.append(error.getDefaultMessage()).append(",");
        }
        errorMsg.delete(errorMsg.length() - 1, errorMsg.length());

        ServiceResult resultBean =new ServiceResult();
        resultBean.setCode(BaseErrorCode.PARAMETER_ERROR.getCode());
        resultBean.setMessage(BaseErrorCode.PARAMETER_ERROR.getError() + " : " + errorMsg.toString());
        return resultBean;
    }

}
