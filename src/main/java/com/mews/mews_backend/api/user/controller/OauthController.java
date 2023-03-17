package com.mews.mews_backend.api.user.controller;

import com.mews.mews_backend.api.user.dto.Req.PostAdminLoginReq;
import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    //회원가입
    @PostMapping(value = "/login")
    public ResponseEntity<UserDto.socialLoginResponse> userLogin(
            @RequestBody final UserDto.register request) throws IOException
    {
        return  oauthService.socialRegister(request);
    }

    //어드민 로그인
    @PostMapping(value = "/admin/login")
    public  ResponseEntity<UserDto.tokenResponse> adminLogin(
            @RequestBody PostAdminLoginReq postAdminLoginReq)
    {
        return oauthService.adminLogin(postAdminLoginReq);
    }

    //토큰 재발급
    @GetMapping("/regenerateToken")
    public ResponseEntity<UserDto.tokenResponse> reissue(
            @RequestHeader(value = "REFRESH_TOKEN") String rtk
    ) {
        log.info("refresh 토큰 재발급");
        return oauthService.reissue(rtk);
    }



}
