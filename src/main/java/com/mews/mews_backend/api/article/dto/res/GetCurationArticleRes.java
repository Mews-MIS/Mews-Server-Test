package com.mews.mews_backend.api.article.dto.res;

import lombok.Builder;
import lombok.Getter;
import com.mews.mews_backend.domain.article.entity.Article;

import java.util.List;

@Getter
public class GetCurationArticleRes {

    private Article article;

    private List<String> editors;

    @Builder
    public GetCurationArticleRes(Article article, List<String> editors) {
        this.article = article;
        this.editors = editors;
    }

}
