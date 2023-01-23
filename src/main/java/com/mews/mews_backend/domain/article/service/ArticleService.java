package com.mews.mews_backend.domain.article.service;

import com.mews.mews_backend.api.article.dto.PostArticleReq;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void register(PostArticleReq postArticleReq){
        Article article = buildArticle(postArticleReq);
        articleRepository.save(article);
    }

    private Article buildArticle(PostArticleReq postArticleReq){
        Article article= Article.builder()
                .title(postArticleReq.getTitle())
                .content(postArticleReq.getContent())
                .type(postArticleReq.getType())
                .build();
        return article;
    }
}
