package com.sustech.regency.web.handler;

import com.sustech.regency.web.vo.ApiResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

//只能捕获到API阶段的Exception, 无法捕获filter链中的Exception
@RestControllerAdvice
public class MyExceptionHandler {

    @SuppressWarnings("ConstantConditions")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String field = fieldError.getField();
        return new ApiResponse(400, "["+field+"] "+ fieldError.getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getMessage().split(" ")[1];
        String field = e.getMessage().split(":")[0].split("\\.")[1];
        return new ApiResponse(400, "["+field+"] "+ message);
    }

    @ExceptionHandler(ApiException.class)
    public ApiResponse handleApiException(ApiException e) {
        return new ApiResponse(e.getCode(), e.getMessage());
    }
}
