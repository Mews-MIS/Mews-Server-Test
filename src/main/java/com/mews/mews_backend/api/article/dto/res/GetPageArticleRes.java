package com.mews.mews_backend.api.article.dto.res;

import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.Getter;

import java.util.List;

@Getter
public class GetPageArticleRes {

    private Article article;

    private List<Editor> editorList;

    public GetPageArticleRes(Article article, List<Editor> editors) {
        this.article = article;
        this.editorList = editors;
    }
}
