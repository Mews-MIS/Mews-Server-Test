package com.mews.mews_backend.domain.article.repository;

import com.mews.mews_backend.domain.article.entity.Views;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewsRepository extends JpaRepository<Views, Long> {
}
