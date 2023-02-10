package com.mews.mews_backend.api.user.controller;

import com.mews.mews_backend.api.user.dto.GetMyPageArticleRes;
import com.mews.mews_backend.api.user.dto.GetMyPageRes;
import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Slf4j
public class MyPageController {
    private final MyPageService myPageService;

    //프로필 정보
    @GetMapping(value="/{userId}")
    public ResponseEntity<GetMyPageRes> getUserInfo(@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(myPageService.getUserInfo(userId), HttpStatus.OK);
    }

    //프로필 편집
    @PatchMapping(value= "/{userId}")
    public ResponseEntity<String> updateMyPage(@PathVariable("userId") Integer userId, @RequestBody UserDto.updateProfile profile) {
        log.info("유저 프로필 업데이트");
        myPageService.updateUser(userId, profile);
        return new ResponseEntity<>("UPDATE USERPROFILE", HttpStatus.OK);
    }

    //북마크 기능
    @PostMapping(value="/{userId}/bookmark/{articleId}")
    public ResponseEntity<String> addBookmark(@PathVariable("userId") Integer userId, @PathVariable("articleId") Integer articleId) {
        log.info("북마크 추가");
        myPageService.insertBookmark(userId,articleId);
        return new ResponseEntity<>("SUCCESS BOOKMARK", HttpStatus.OK);
    }

    //내 북마크 모아보기
    @GetMapping(value="/{userId}/myBookmark")
    public ResponseEntity<List<GetMyPageArticleRes>> getMyBookmark(@PathVariable("userId") Integer userId) {
        log.info("내 북마크 글 가져오기");
        List<GetMyPageArticleRes> getMyPageBookmarkResList = myPageService.getMyBookmark(userId);
        return new ResponseEntity<>(getMyPageBookmarkResList,HttpStatus.OK);
    }

    //게시글 좋아요
    @PostMapping("/{userId}/article/{articleId}/like")
    public ResponseEntity<String> likeArticle(@PathVariable("userId") Integer userId, @PathVariable("articleId") Integer articleId){
        log.info("좋아요");
        myPageService.likeArticle(userId, articleId);
        return new ResponseEntity<>("SUCCESS LIKE",HttpStatus.OK);
    }

    //내 좋아요 글 모아보기
    @GetMapping(value="/{userId}/myLikeArticle")
    public ResponseEntity<List<GetMyPageArticleRes>> getMyLikeArticle(@PathVariable("userId") Integer userId) {
        log.info("내 좋아요 모아보기");
        List<GetMyPageArticleRes> getMyPageLikeArticleResList = myPageService.getLikeArticle(userId);
        return new ResponseEntity<>(getMyPageLikeArticleResList,HttpStatus.OK);
    }
}
