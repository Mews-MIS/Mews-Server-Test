package com.mews.mews_backend.api.show.dto.request;

import com.mews.mews_backend.domain.banner.entity.Banner;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostBannerReq {

    @NotNull
    @ApiModelProperty(notes = "아티클 ID", example = "1")
    private Integer article_id;

    public Banner toEntity(PostBannerReq postBannerReq) {
        Banner banner = Banner.builder()
                .article_id(postBannerReq.getArticle_id())
                .build();

        return banner;
    }
}
