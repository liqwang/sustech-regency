package com.sustech.regency.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("unused")
@Data
@AllArgsConstructor
public class ApiResponse<T> {

    public static final ApiResponse SUCCESS = new ApiResponse(200, "OK");

	@ApiModelProperty(example="200")
	private int code;
	@ApiModelProperty(example="\"ok\"")
	private String message;
	private T data;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ApiResponse success() {
        return SUCCESS;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "OK", data);
    }
}
