package com.mews.mews_backend.api.article.dto.res;

import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.Getter;

import java.util.List;

@Getter
public class GetPageArticleRes {

    private Article article;

    private List<Editor> editorList;

    //북마크 여부
    private boolean isBookmarked;

    //좋아요 여부
    private boolean isLiked;

    public GetPageArticleRes(Article article, List<Editor> editors, boolean isLiked,boolean isBookmarked) {
        this.article = article;
        this.editorList = editors;
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;
    }
}
