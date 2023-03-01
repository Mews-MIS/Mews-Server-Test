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
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    @CrossOrigin
    @GetMapping(value = "/google")
    public void socialLoginType() {
        String socialLoginType = "google";
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", "google");
        oauthService.request(socialLoginType);
    }

    @GetMapping(value = "/google/callback")
    public ResponseEntity<UserDto.socialLoginResponse> callback(
            @RequestParam(name = "code") String code) throws IOException {
        String socialLoginType = "google";
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        return oauthService.oauthLogin(socialLoginType,code);
    }

    //회원가입
    @PostMapping(value = "/sign-up")
    public ResponseEntity<UserDto.socialLoginResponse> signUp(@RequestBody final UserDto.register request) throws IOException {
        log.info("회원가입 진행");
        //TODO 회원가입 변경사항 처리
        return  oauthService.socialRegister(request);
    }

//    //어드민 회원가입
//    @PostMapping(value = "/admin/register")
//    public void adminRegister(){
//        oauthService.adminRegister();
//    }

    //어드민 로그인
    @PostMapping(value = "/admin/login")
    public  ResponseEntity<UserDto.tokenResponse> adminLogin(@RequestBody PostAdminLoginReq postAdminLoginReq){
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
