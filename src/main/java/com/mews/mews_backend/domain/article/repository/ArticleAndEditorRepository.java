package com.mews.mews_backend.domain.article.repository;

import com.mews.mews_backend.domain.article.entity.ArticleAndEditor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleAndEditorRepository  extends JpaRepository<ArticleAndEditor, Integer> {
}
