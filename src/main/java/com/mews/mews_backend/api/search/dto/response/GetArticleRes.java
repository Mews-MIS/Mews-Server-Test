package com.mews.mews_backend.api.search.dto.response;

import com.mews.mews_backend.domain.article.entity.Article;
import lombok.Getter;

@Getter
public class GetArticleRes {

    private Integer id;

    private String imgUrl;
    private String title;

    public GetArticleRes(Article article) {
        this.id = article.getId();
        this.imgUrl = article.getFileUrls().get(0);
        this.title = article.getTitle();
    }
}
