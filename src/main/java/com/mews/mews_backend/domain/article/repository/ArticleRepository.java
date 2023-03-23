package com.mews.mews_backend.domain.article.repository;

import com.mews.mews_backend.api.article.dto.res.GetArticleRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.entity.Views;
import com.mews.mews_backend.domain.editor.entity.Editor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query(value = "select a.id, a.created_at, a.modified_at, a.content, a.file_urls, a.like_count, a.title, a.type, a.views_id " +
            "from article a " +
            "left join article_and_editor aae on a.id = aae.article_id " +
            "left join editor e on aae.editor_id = e.editor_id " +
            "where e.name like %:searchKeyword% " +
            "union " +
            "select a.id, a.created_at, a.modified_at, a.content, a.file_urls, a.like_count, a.title, a.type, a.views_id " +
            "from article a " +
            "where a.title like %:searchKeyword%", nativeQuery = true)
    List<Article> findAllByKeyword(@Param("searchKeyword") String keyword);

    Page<Article> findAllByIsDeletedFalseOrderByModifiedAtDesc(Pageable pageable);

    List<Article> findAllByType(String type);

    List<Article> findTop5ByOrderByViewsViewsDesc();

    Integer countByIsDeletedFalse();

}
