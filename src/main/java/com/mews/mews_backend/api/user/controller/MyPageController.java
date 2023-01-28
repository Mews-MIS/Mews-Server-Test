package com.mews.mews_backend.api.user.controller;

import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
@Slf4j
public class MyPageController {
    private final MyPageService myPageService;
    //프로필 편집
    @PatchMapping(value= "/mypage/{userId}")
    public ResponseEntity<String> updateMyPage(@PathVariable("userId") Integer userId, @RequestBody UserDto.updateProfile profile) {
        log.info("유저 프로필 업데이트");
        myPageService.updateUser(userId, profile);
        return new ResponseEntity<>("UPDATE USERPROFILE", HttpStatus.OK);
    }
}
