package com.mews.mews_backend.global.config.Jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component//유효한 자격증명을 제공하지 않고 접근하려 할 때 401 Unauthroized 에러를 리턴할 클래스
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        log.info("토큰 넣어주세요");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"UnAuthorized");
    }
}