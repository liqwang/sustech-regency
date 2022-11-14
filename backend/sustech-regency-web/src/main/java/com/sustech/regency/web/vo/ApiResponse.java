package com.sustech.regency.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("unused")
@Data
@AllArgsConstructor
public class ApiResponse<T> {

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
        return success(null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "OK", data);
    }

	public static ApiResponse badRequest(String message){
		return new ApiResponse<>(400,message);
	}

	public static ApiResponse internalServerError(){
		return internalServerError("Internal Server Error");
	}

	public static ApiResponse internalServerError(String message){
		return new ApiResponse(500,message);
	}
}
