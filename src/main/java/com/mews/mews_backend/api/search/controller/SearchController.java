package com.mews.mews_backend.api.search.controller;

import com.mews.mews_backend.api.search.dto.response.PostArticleRes;
import com.mews.mews_backend.api.search.dto.response.PostEditorRes;
import com.mews.mews_backend.api.search.dto.response.PostSearchRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.service.ArticleService;
import com.mews.mews_backend.domain.editor.service.EditorService;
import com.mews.mews_backend.domain.search.service.SearchService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Search API"})
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping(value = "/{keyword}")
    public ResponseEntity<PostSearchRes> Search(@PathVariable ("keyword") String string) {
        List<PostArticleRes> postArticleResList = searchService.findArticle(string);
        List<PostEditorRes> postEditorResList = searchService.findEditor(string);

        PostSearchRes postSearchRes = new PostSearchRes(postArticleResList, postEditorResList);

        return ResponseEntity.ok(postSearchRes);
    }
}
