package com.mews.mews_backend.api.user.controller;

import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.service.OauthService;
import com.mews.mews_backend.global.config.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class OauthController {

    private final OauthService oauthService;

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

    //성별, 어드민인지 아닌지 여부 어떻게 받나?
    //사용자가 로그인 진행
    @PostMapping(value = "/sign-up")
    public ResponseEntity<UserDto.socialLoginResponse> singUp(@RequestBody final UserDto.register request) throws IOException {
        log.info("회원가입 진행");
        return  oauthService.socialRegister(request);
    }

}
