package com.mews.mews_backend.api.article.controller;

import com.mews.mews_backend.api.article.dto.req.PatchArticleReq;
import com.mews.mews_backend.api.article.dto.req.PostArticleReq;
import com.mews.mews_backend.api.article.dto.res.GetAllArticleRes;
import com.mews.mews_backend.api.article.dto.res.GetArticleRes;
import com.mews.mews_backend.api.article.dto.res.GetMainArticleRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.service.ArticleService;
import com.mews.mews_backend.domain.article.service.ViewsService;
import com.nimbusds.jose.util.Pair;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Article API"})
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final ViewsService viewsService;

    @ApiOperation("뉴스 게시글 작성")
    @PostMapping("/post")
    public ResponseEntity<String> postArticle(@RequestPart(value = "data", required = false) PostArticleReq postArticleReq){
        articleService.postArticle(postArticleReq);
        return ResponseEntity.ok("post success");
    }

    @ApiOperation("뉴스 전체 조회(페이지네이션)")
    @GetMapping("/all")
    public ResponseEntity<GetAllArticleRes> getAllArticle(@Positive @RequestParam Integer page){
        List<Article> articles = articleService.getAllArticle(page-1); // 0번 페이지부터 시작하므로 -1
        Integer pageCount = articleService.getPageCount();

        GetAllArticleRes getAllArticleRes = GetAllArticleRes.from(pageCount, articles);
        return ResponseEntity.ok(getAllArticleRes);
    }

    @ApiOperation("뉴스 게시글 조회(조회수 증가)")
    @GetMapping("{id}")
    public ResponseEntity<GetArticleRes> getArticle(@PathVariable("id") Integer articleId){
        Integer viewId = viewsService.getViewId(articleId);
        viewsService.updateView(viewId); // 조회수 증가
        GetArticleRes getArticleRes = articleService.viewArticle(articleId);
        return ResponseEntity.ok(getArticleRes);
    }

    @ApiOperation("뉴스 게시글 수정")
    @PatchMapping("/update")
    public ResponseEntity<String> updateArticle(@RequestPart(value = "data") PatchArticleReq patchArticleReq){
        articleService.updateArticle(patchArticleReq);
        return ResponseEntity.ok("update success");
    }

    @ApiOperation("뉴스 게시글 삭제")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") Integer articleId){
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok("delete success");
    }

    @ApiOperation("해당 카테고리 뉴스 전체 조회")
    @GetMapping("/allType")
    public ResponseEntity<List<Article>> getAllTypeArticle(@RequestParam("type") String type){
        List<Article> articleList = articleService.getAllTypeArticle(type);
        return ResponseEntity.ok(articleList);
    }

    @ApiOperation("조회수 top 5 조회")
    @GetMapping("/top")
    public ResponseEntity<List<Article>> getFiveTopViewsArticle(){
        List<Article> articleList = articleService.getFiveTopViewsArticle();
        return ResponseEntity.ok(articleList);
    }

    @ApiOperation("내가 구독한 게시글 조회")
    @GetMapping("/{user_id}/subscribe")
    public ResponseEntity<List<GetMainArticleRes>> getSubscribeArticle(@PathVariable("user_id") Integer id) {
        List<GetMainArticleRes> getMainArticleRes = articleService.getSubscribeArticle(id);

        return ResponseEntity.ok(getMainArticleRes);

    }

    //북마크 기능
    @PostMapping(value="/{articleId}/user/{userId}/bookmark")
    public ResponseEntity<String> addBookmark(@PathVariable("userId") Integer userId, @PathVariable("articleId") Integer articleId) {
        return new ResponseEntity<>(articleService.insertBookmark(userId,articleId), HttpStatus.OK);
    }

    //게시글 좋아요
    @PostMapping("/{articleId}/user/{userId}/like")
    public ResponseEntity<String> addlikeArticle(@PathVariable("userId") Integer userId, @PathVariable("articleId") Integer articleId){
        return new ResponseEntity<>(articleService.insertlikeArticle(userId, articleId), HttpStatus.OK);
    }
}
