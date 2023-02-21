package com.mews.mews_backend.api.curation.dto.response;

import com.mews.mews_backend.domain.curation.entity.Curation;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCurationRes {

    @ApiModelProperty(notes = "큐레이션 제목")
    private String title;

    @ApiModelProperty(notes = "아티클 리스트")
    private List<String> list;

    @ApiModelProperty(notes = "최종 수정 시각")
    private LocalDateTime modifiedAt;

    // Entity To DTO
    public GetCurationRes(Curation curation) {
        this.title = curation.getTitle();
        this.list = curation.getList();
        this.modifiedAt = curation.getModifiedAt();
    }
}
