package com.mews.mews_backend.api.curation.dto.request;

import com.mews.mews_backend.domain.curation.entity.Curation;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostCurationReq {

    @NotBlank
    @ApiModelProperty(notes = "큐레이션 제목")
    private String title;

    @ApiModelProperty(notes = "아티클 ID 리스트")
    private List<String> list;

    @NotBlank
    @ApiModelProperty(notes = "마크다운 본문", example = "### 안녕하세요.")
    private String body;

    @NotBlank
    @ApiModelProperty(notes = "인터뷰 내용")
    private String interview;

    @ApiModelProperty(notes = "첨부파일 url", example = "[\"www.google.com\", \"www.naver.com'\"]")
    private List<String> fileUrls;

    public Curation toEntity(PostCurationReq postCurationReq) {
        Curation curation = Curation.builder()
                .title(postCurationReq.getTitle())
                .list(postCurationReq.getList())
                .body(postCurationReq.getBody())
                .interview(postCurationReq.getInterview())
                .fileUrls(postCurationReq.getFileUrls())
                .open(Boolean.TRUE)
                .build();

        return curation;
    }
}
