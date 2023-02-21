package com.mews.mews_backend.domain.article.repository;

import com.mews.mews_backend.domain.article.entity.Views;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViewsRepository extends JpaRepository<Views, Integer> {
    @Modifying
    @Query("UPDATE Views v SET v.views = v.views + 1 WHERE v.views_id = :id")
    int updateView(Integer id);
}
