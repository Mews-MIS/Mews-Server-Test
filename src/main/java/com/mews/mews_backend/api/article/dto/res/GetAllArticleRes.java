package com.mews.mews_backend.api.article.dto.res;

import com.mews.mews_backend.domain.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetAllArticleRes {
    Integer pageCount;
    List<Article> articles;

    public static GetAllArticleRes from(Integer pageCount, List<Article> articles){
        return GetAllArticleRes.builder()
                .pageCount(pageCount)
                .articles(articles)
                .build();
    }
}
