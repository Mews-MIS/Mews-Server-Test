package com.mews.mews_backend.api.article.controller;

import com.mews.mews_backend.api.article.dto.PostArticleReq;
import com.mews.mews_backend.domain.article.service.ArticleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Article API"})
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/post")
    public ResponseEntity<String> postArticle(@Valid @RequestBody PostArticleReq postArticleReq){
        articleService.register(postArticleReq);
        return ResponseEntity.ok("post success");
    }
}
