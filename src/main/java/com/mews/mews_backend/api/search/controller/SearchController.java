package com.mews.mews_backend.api.search.controller;

import com.mews.mews_backend.api.search.dto.response.PostArticleRes;
import com.mews.mews_backend.api.search.dto.response.PostEditorRes;
import com.mews.mews_backend.api.search.dto.response.PostSearchRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.service.ArticleService;
import com.mews.mews_backend.domain.editor.service.EditorService;
import com.mews.mews_backend.domain.search.service.SearchRedisService;
import com.mews.mews_backend.domain.search.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Search API"})
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;
    private final SearchRedisService searchRedisService;

    @ApiOperation("검색")
    @GetMapping(value = "/{keyword}")
    public ResponseEntity<PostSearchRes> Search(@PathVariable("keyword") String searchWord) {
        List<PostArticleRes> postArticleResList = searchService.findArticle(searchWord);
        List<PostEditorRes> postEditorResList = searchService.findEditor(searchWord);

        PostSearchRes postSearchRes = new PostSearchRes(postArticleResList, postEditorResList);
        searchRedisService.increaseSearchWords(searchWord);

        return ResponseEntity.ok(postSearchRes);
    }

    @ApiOperation("인기 검색어 가져오기")
    @GetMapping("/popular")
    public ResponseEntity<List<String>> getPopularSearchWords(){
        List<String> getPopularSearchWords = searchRedisService.getPopularSearchWords();
        return ResponseEntity.ok(getPopularSearchWords);
    }

}
