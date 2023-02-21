package com.mews.mews_backend.api.curation.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCurationTitleRes {

    @ApiModelProperty(notes = "id")
    private Integer id;

    @ApiModelProperty(notes = "제목")
    private String title;
}
