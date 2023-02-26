package com.mews.mews_backend.api.article.dto.res;

import com.mews.mews_backend.api.editor.dto.response.EditorForArticle;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleForEditor {
    // Editor가 작성한 기사 목록 반환 시 사용하게 될 DTO

    private Integer id;

    private String title;

    private String type; // 콘텐츠 타입

    private List<String> fileUrls;

    private List<EditorForArticle> editor;

    public ArticleForEditor(Article article, List<EditorForArticle> editors) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.type = article.getType();
        this.fileUrls = article.getFileUrls();
        this.editor = editors;
    }
}
