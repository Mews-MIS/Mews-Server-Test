package com.mews.mews_backend.api.user.controller;

import com.mews.mews_backend.api.user.dto.Req.PatchUserProfileReq;
import com.mews.mews_backend.api.user.dto.Res.GetMyPageArticleRes;
import com.mews.mews_backend.api.user.dto.Res.GetMyPageRes;
import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Slf4j
public class MyPageController {
    private final MyPageService myPageService;

    //프로필 정보 get
    @GetMapping(value="/profile/{userId}")
    public ResponseEntity<GetMyPageRes> getUserProfile(@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(myPageService.getUserInfo(userId), HttpStatus.OK);
    }

    //프로필 편집
    @PatchMapping(value="/profile/{userId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable("userId") Integer userId,
                                               @RequestPart(value = "data") PatchUserProfileReq profile,
                                               @RequestPart(value="file", required = false) MultipartFile multipartFile) {
        myPageService.updateUserInfo(userId, profile, multipartFile);
        return new ResponseEntity<>("UPDATE USERPROFILE", HttpStatus.OK);
    }

    //내 북마크 모아보기
    @GetMapping(value="/{userId}/myBookmark")
    public ResponseEntity<List<GetMyPageArticleRes>> getMyBookmark(@PathVariable("userId") Integer userId) {
        List<GetMyPageArticleRes> getMyPageBookmarkResList = myPageService.getMyBookmark(userId);
        return new ResponseEntity<>(getMyPageBookmarkResList,HttpStatus.OK);
    }

    //내 좋아요 글 모아보기
    @GetMapping(value="/{userId}/myLikeArticle")
    public ResponseEntity<List<GetMyPageArticleRes>> getMyLikeArticle(@PathVariable("userId") Integer userId) {
        List<GetMyPageArticleRes> getMyPageLikeArticleResList = myPageService.getLikeArticle(userId);
        return new ResponseEntity<>(getMyPageLikeArticleResList,HttpStatus.OK);
    }


    //필진 구독하기
    @PostMapping(value="/{userId}/editor/{editorId}")
    public ResponseEntity<String> addEditor(@PathVariable("userId") Integer userId, @PathVariable("editorId") Integer editorId){
        myPageService.insertEditor(userId, editorId);
        return new ResponseEntity<>("ADD EDITOR",HttpStatus.OK);
    }

    //필진 글 보여주기
    @GetMapping(value="/{userId}/editor/{editorId}")
    public ResponseEntity<List<GetMyPageArticleRes>> getEditorArticle(@PathVariable("userId") Integer userId, @PathVariable("editorId") Integer editorId){
        return new ResponseEntity<>(myPageService.getEditorArticles(userId, editorId), HttpStatus.OK);
    }
}
