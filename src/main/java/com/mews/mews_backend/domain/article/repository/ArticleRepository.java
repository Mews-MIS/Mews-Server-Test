package com.mews.mews_backend.domain.article.repository;

import com.mews.mews_backend.api.article.dto.res.GetArticleRes;
import com.mews.mews_backend.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.List;

@Repository
@EnableJpaRepositories
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findAllByTitle(String string);
=======
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Page<Article> findAllByOrderById(Pageable pageable);
>>>>>>> 09fc90ccff74949d07ff7f916e7b97eacfb39e52
}
