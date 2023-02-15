package com.mews.mews_backend.domain.article.repository;

import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.entity.ArticleAndEditor;
import com.mews.mews_backend.domain.editor.entity.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleAndEditorRepository  extends JpaRepository<ArticleAndEditor, Integer> {

    List<ArticleAndEditor> findAllByEditorOrderByModifiedAt(Editor editor);
    List<ArticleAndEditor> findAllByArticle(Article article);
}
