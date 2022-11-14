package com.sustech.regency.web.handler;

import lombok.Getter;

/**
 * Refer: <a href="https://www.devglan.com/spring-security/exception-handling-in-spring-security">Exception handling in Spring Security</a>
 */
@Getter
public class ApiException extends RuntimeException {

    private final int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static ApiException badRequest(String message) {
        return new ApiException(400, message);
    }

    public static ApiException internalServerError(){
        return internalServerError("Internal server error");
    }

    public static ApiException internalServerError(String message){
        return new ApiException(500,message);
    }
}
