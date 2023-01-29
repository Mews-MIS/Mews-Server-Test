package com.mews.mews_backend.api.user.controller;

import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("app")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //로그아웃
    @PatchMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(value = "Authorization") String auth
    ) {
        return userService.logout(auth);
    }

    //회원탈퇴
    @PatchMapping("/withdrawal")
    public ResponseEntity<String> withdraw(
            @RequestHeader(value = "Authorization") String auth
    ){
        return userService.delete(auth);
    }
}
