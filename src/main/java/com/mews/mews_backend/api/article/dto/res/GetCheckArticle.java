package com.mews.mews_backend.api.article.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetCheckArticle {

    Boolean bookmark;

    Boolean like;

    // To DTO
    @Builder
    public GetCheckArticle(Boolean bookmark, Boolean like) {
        this.bookmark = bookmark;
        this.like = like;
    }
}
