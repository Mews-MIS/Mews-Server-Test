package com.mews.mews_backend.api.search.dto.response;

import com.mews.mews_backend.domain.article.entity.Article;
import lombok.Getter;

@Getter
public class PostArticleRes {

    private Integer id;

    private String title;

    public PostArticleRes(Article article) {
        this.id = article.getArticle_id();
        this.title = article.getTitle();
    }
}
