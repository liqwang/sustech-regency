package com.sustech.regency.web.handler;

import com.sustech.regency.web.vo.ApiResponse;
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
        return new ApiResponse(400, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse handleConstraintViolationException(ConstraintViolationException e) {
        return new ApiResponse(400, e.getMessage().split(" ")[1]);
    }

    @ExceptionHandler(ApiException.class)
    public ApiResponse handleApiException(ApiException e) {
        return new ApiResponse(e.getCode(), e.getMessage());
    }
}
