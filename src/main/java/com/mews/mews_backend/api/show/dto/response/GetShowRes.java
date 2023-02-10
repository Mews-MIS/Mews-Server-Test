package com.mews.mews_backend.api.show.dto.response;

import com.mews.mews_backend.domain.show.entity.Show;
import lombok.Getter;

@Getter
public class GetShowRes {

    private Integer id;

    private Integer article_id;

    public GetShowRes(Show show) {
        this.id = show.getId();
        this.article_id = show.getArticle_id();
    }
}
