package com.sustech.regency.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sustech.regency.web.vo.ApiResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Refer: <a href="https://www.devglan.com/spring-security/exception-handling-in-spring-security">Exception handling in Spring Security</a>
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        ApiResponse response = new ApiResponse(401, "Unauthorized, no JWT or the JWT may is expired");
        httpServletResponse.setStatus(401);
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
    }
}
