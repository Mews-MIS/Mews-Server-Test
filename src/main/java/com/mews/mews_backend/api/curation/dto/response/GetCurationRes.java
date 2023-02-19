package com.mews.mews_backend.api.curation.dto.response;

import com.mews.mews_backend.domain.curation.entity.Curation;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetCurationRes {

    @ApiModelProperty(notes = "큐레이션 제목")
    private String title;

    @ApiModelProperty(notes = "아티클 리스트")
    private List<String> list;

    @ApiModelProperty(notes = "큐레이션 본문")
    private String body;

    @ApiModelProperty(notes = "큐레이션 인터뷰")
    private String interview;

    @ApiModelProperty(notes = "첨부 파일 리스트")
    private List<String> fileUrls;

    @ApiModelProperty(notes = "최종 수정 시각")
    private LocalDateTime modifiedAt;

    // Entity To DTO
    public GetCurationRes(Curation curation) {
        this.title = curation.getTitle();
        this.list = curation.getList();
        this.body = curation.getBody();
        this.interview = curation.getInterview();
        this.fileUrls = curation.getFileUrls();
        this.modifiedAt = curation.getModifiedAt();
    }
}
