package com.mews.mews_backend.api.show.dto.response;

import com.mews.mews_backend.domain.banner.entity.Banner;
import lombok.Getter;

@Getter
public class GetBannerRes {

    private Integer id;

    private Integer article_id;

    public GetBannerRes(Banner banner) {
        this.id = banner.getId();
        this.article_id = banner.getArticle_id();
    }
}
