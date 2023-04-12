package com.mews.mews_backend.domain.user.repository;

import com.mews.mews_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByUserEmail(String email);
    Optional<User> findByUserEmail(String email);

    @Modifying
    @Query(nativeQuery = true, value = "update users u set u.subscribe_count = u.subscribe_count - 1 " +
            "where u.user_id in ( " +
            "select s.user_id " +
            "from subscribe s " +
            "where s.editor_id = :id) and u.subscribe_count > 0")
    void updateSubCntById(@Param("id") Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "update users u set u.like_count = u.like_count - 1 " +
            "where u.user_id in ( " +
            "select l.user_id " +
            "from likes l " +
            "where l.article_id = :id) and u.like_count > 0")
    void updateLikeCntById(@Param("id") Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "update users u set u.bookmark_count = u.bookmark_count - 1 " +
            "where u.user_id in ( " +
            "select b.user_id " +
            "from bookmark b " +
            "where b.article_id = :id) and u.bookmark_count > 0")
    void updateBookmarkCntById(@Param("id") Integer id);
}