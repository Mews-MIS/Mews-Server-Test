package com.mews.mews_backend.api.search.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostSearchReq {

    @NotBlank
    @ApiModelProperty(notes = "검색 키워드", example = "작성자")
    private String keyword;
}
