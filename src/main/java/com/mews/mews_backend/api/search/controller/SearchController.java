package com.mews.mews_backend.api.search.controller;

import com.mews.mews_backend.api.search.dto.response.GetArticleRes;
import com.mews.mews_backend.api.search.dto.response.GetEditorRes;
import com.mews.mews_backend.api.search.dto.response.GetSearchRes;
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
    public ResponseEntity<GetSearchRes> Search(@PathVariable("keyword") String searchWord) {
        List<GetArticleRes> getArticleResList = searchService.findArticle(searchWord);
        List<GetEditorRes> getEditorResList = searchService.findEditor(searchWord);

        GetSearchRes getSearchRes = new GetSearchRes(getArticleResList, getEditorResList);
        searchRedisService.increaseSearchWords(searchWord);

        return ResponseEntity.ok(getSearchRes);
    }

    @ApiOperation("인기 검색어 가져오기")
    @GetMapping("/popular")
    public ResponseEntity<List<String>> getPopularSearchWords(){
        List<String> getPopularSearchWords = searchRedisService.getPopularSearchWords();
        return ResponseEntity.ok(getPopularSearchWords);
    }

}
