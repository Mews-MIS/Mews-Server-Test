package com.mews.mews_backend.api.article.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostArticleReq {

    @NotBlank
    @ApiModelProperty(notes = "뉴스 제목", example = "속보 mews 조회수 500만 돌파")
    private String title; // 제목

    @NotBlank
    @ApiModelProperty(notes = "마크다운 내용", example = "<h2>기사 내용</h2>")
    private String content; // 마크다운 내용
}
