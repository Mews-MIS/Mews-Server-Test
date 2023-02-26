package com.mews.mews_backend.api.editor.dto.response;

import com.mews.mews_backend.api.article.dto.res.ArticleForEditor;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.Getter;

import java.util.List;

@Getter
public class GetEditorAndArticleRes {

    private Integer id;

    private String name;

    private List<ArticleForEditor> articles;

    // Entity to DTO
    public GetEditorAndArticleRes(Editor editor, List<ArticleForEditor> articles) {
        this.id = editor.getId();
        this.name = editor.getName();
        this.articles = articles;
    }
}
