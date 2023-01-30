package com.mews.mews_backend.api.article.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatchArticleReq {

    @NotNull
    @ApiModelProperty(notes = "뉴스 게시물 번호", example = "1")
    private Integer id; // 기사 아이디

    @ApiModelProperty(notes = "뉴스 제목", example = "속보 mews 조회수 500만 돌파")
    private String title; // 제목

    @ApiModelProperty(notes = "마크다운 내용", example = "<h2>기사 내용</h2>")
    private String content; // 마크다운 내용

    @ApiModelProperty(notes = "타입", example = "article")
    private String type; // 타입
}
