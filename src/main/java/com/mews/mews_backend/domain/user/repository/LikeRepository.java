package com.mews.mews_backend.domain.user.repository;

import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.user.entity.Bookmark;
import com.mews.mews_backend.domain.user.entity.Like;
import com.mews.mews_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Integer> {

//    @Query("select l from Like l where l.user.id = :userId and l.article.article_id = :articleId")
//    List<Like> existsByIdAndArticleId(Integer userId, Integer articleId);

    @Modifying
    @Query("delete from Like l where l.user.id = :userId and l.article.article_id = :articleId")
    void deleteByIdAndArticleId(Integer userId, Integer articleId);

    @Query("select l from Like l where l.user.id = :userId ORDER BY l.modifiedAt DESC")
    List<Like> findAllByUserId(Integer userId);


    List<Like> findAllByUserOrderByModifiedAtDesc(User user);

    boolean existsByArticleAndUser(Article article, User user);
}
