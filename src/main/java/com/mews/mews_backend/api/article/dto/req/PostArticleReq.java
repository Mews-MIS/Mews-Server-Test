package com.mews.mews_backend.api.article.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

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

    @NotBlank
    @ApiModelProperty(notes = "타입", example = "article")
    private String type; // 타입

    @ApiModelProperty(notes = "첨부파일 url", example = "[\"www.google.com\", \"www.naver.com'\"]")
    private List<String> fileUrls; // 첨부파일 urls

    //필진 리스트
    @ApiModelProperty(notes = "필진id list", example="[1,2]")
    private List<Integer> editors;
}
