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
        log.info("유저 프로필 업데이트");
        myPageService.updateUserInfo(userId, profile, multipartFile);
        return new ResponseEntity<>("UPDATE USERPROFILE", HttpStatus.OK);
    }

    //북마크 기능
    @PostMapping(value="/{userId}/bookmark/{articleId}")
    public ResponseEntity<String> addBookmark(@PathVariable("userId") Integer userId, @PathVariable("articleId") Integer articleId) {
        log.info("북마크 추가");
        boolean result = myPageService.insertBookmark(userId,articleId);
        String resultMessage = "";
        if(result==true){
            resultMessage = "ADD BOOKMARK";
        } else {
            resultMessage = "DELETE BOOKMARK";
        }
        return new ResponseEntity<>(resultMessage, HttpStatus.OK);
    }

    //내 북마크 모아보기
    @GetMapping(value="/{userId}/myBookmark")
    public ResponseEntity<List<GetMyPageArticleRes>> getMyBookmark(@PathVariable("userId") Integer userId) {
        log.info("내 북마크 글 가져오기");
        List<GetMyPageArticleRes> getMyPageBookmarkResList = myPageService.getMyBookmark(userId);
        return new ResponseEntity<>(getMyPageBookmarkResList,HttpStatus.OK);
    }

    //게시글 좋아요
    @PostMapping("/{userId}/like/{articleId}")
    public ResponseEntity<String> addlikeArticle(@PathVariable("userId") Integer userId, @PathVariable("articleId") Integer articleId){
        log.info("좋아요");
        boolean result = myPageService.insertlikeArticle(userId, articleId);
        String resultMessage = "";
        if(result==true){
            resultMessage = "ADD LIKEARTICLE";
        } else {
            resultMessage = "DELETE LIKEARTICLE";
        }
        return new ResponseEntity<>(resultMessage, HttpStatus.OK);
    }

    //내 좋아요 글 모아보기
    @GetMapping(value="/{userId}/myLikeArticle")
    public ResponseEntity<List<GetMyPageArticleRes>> getMyLikeArticle(@PathVariable("userId") Integer userId) {
        log.info("내 좋아요 모아보기");
        List<GetMyPageArticleRes> getMyPageLikeArticleResList = myPageService.getLikeArticle(userId);
        return new ResponseEntity<>(getMyPageLikeArticleResList,HttpStatus.OK);
    }

    //필진 구독하기
    @PostMapping(value="/{userId}/editor/{editorId}")
    public ResponseEntity<String> addEditor(@PathVariable("userId") Integer userId, @PathVariable("editorId") Integer editorId){
        myPageService.insertEditor(userId, editorId);
        return new ResponseEntity<>("ADD EDITOR",HttpStatus.OK);
    }
}
