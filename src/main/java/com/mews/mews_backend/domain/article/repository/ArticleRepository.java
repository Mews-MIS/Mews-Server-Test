package com.mews.mews_backend.domain.article.repository;

import com.mews.mews_backend.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findAllByTitle(String string);
}
