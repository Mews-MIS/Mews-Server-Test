package com.mews.mews_backend.api.show.dto.request;

import com.mews.mews_backend.domain.show.entity.Show;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostShowReq {

    @NotNull
    @ApiModelProperty(notes = "아티클 ID", example = "1")
    private Integer article_id;

    public Show toEntity(PostShowReq postShowReq) {
        Show show = Show.builder()
                .article_id(postShowReq.getArticle_id())
                .build();

        return show;
    }
}
