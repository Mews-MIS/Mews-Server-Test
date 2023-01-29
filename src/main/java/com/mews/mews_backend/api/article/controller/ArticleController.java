package com.mews.mews_backend.api.article.controller;

import com.mews.mews_backend.api.article.dto.req.PostArticleReq;
import com.mews.mews_backend.api.article.dto.res.GetArticleRes;
import com.mews.mews_backend.domain.article.service.ArticleService;
import com.mews.mews_backend.domain.article.service.ViewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Article API"})
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final ViewsService viewsService;

    @ApiOperation("뉴스 게시글 작성")
    @PostMapping("/post")
    public ResponseEntity<String> postArticle(@Valid @RequestBody PostArticleReq postArticleReq){
        articleService.register(postArticleReq);
        return ResponseEntity.ok("post success");
    }

    @ApiOperation("게시글 조회(조회수 증가)")
    @GetMapping("{id}")
    public ResponseEntity<GetArticleRes> getArticle(@PathVariable("id") Integer articleId){
        Integer viewId = viewsService.getViewId(articleId);
        viewsService.updateView(viewId); // 조회수 증가
        GetArticleRes getArticleRes = articleService.viewArticle(articleId);
        return ResponseEntity.ok(getArticleRes);
    }
}
