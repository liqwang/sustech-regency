package com.sustech.regency.web.handler;

import com.sustech.regency.web.vo.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//只能捕获到API阶段的Exception, 无法捕获filter链中的Exception
@RestControllerAdvice
public class ApiExceptionHandler {

	@SuppressWarnings("ConstantConditions")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse handle(MethodArgumentNotValidException e){
		return new ApiResponse(400,e.getBindingResult().getFieldError().getDefaultMessage());
	}

	@ExceptionHandler(ApiException.class)
	public ApiResponse handle(ApiException e){
		return new ApiResponse(e.getCode(),e.getMessage());
	}
}
