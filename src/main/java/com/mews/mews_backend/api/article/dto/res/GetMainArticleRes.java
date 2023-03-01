package com.mews.mews_backend.api.article.dto.res;

import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetMainArticleRes {

    private Article article;
    private List<Editor> editors;

    private Boolean userBookmark;

    private Boolean userLike;

    @Builder
    public GetMainArticleRes(Article article, List<Editor> editors, Boolean userBookmark, Boolean userLike) {
        this.article = article;
        this.editors = editors;
        this.userBookmark = userBookmark;
        this.userLike = userLike;
    }
}
