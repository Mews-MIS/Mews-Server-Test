package com.mews.mews_backend.domain.article.repository;

import com.mews.mews_backend.api.article.dto.res.GetArticleRes;
import com.mews.mews_backend.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Page<Article> findAllByOrderById(Pageable pageable);
}
