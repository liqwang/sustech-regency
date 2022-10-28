package com.sustech.regency.web.handler;

import com.sustech.regency.web.vo.ApiResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ReportAsSingleViolation;

/**
 * 只能捕获到API阶段的Exception, 无法捕获filter链中的Exception<p>
 * 参考：<a href="https://www.nicechiblog.com/article/14/article_1598135490604.html">Spring中的ExceptionHandler</a>
 * <p><p>
 * 如果不使用{@code @}{@link ReportAsSingleViolation}，那么{@link MethodArgumentNotValidException}和{@link ConstraintViolationException}中可能包含多个Violation，最好都加进报错信息里<p>
 * 参考：<a href="https://reflectoring.io/bean-validation-with-spring-boot/">bean-validation-with-spring-boot</a>
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
        return new ApiResponse(400,"Missing param ["+e.getParameterName()+"]");
    }

    @SuppressWarnings("ConstantConditions")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String field = fieldError.getField();
        return new ApiResponse(400, "["+field+"]"+ fieldError.getDefaultMessage());
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
