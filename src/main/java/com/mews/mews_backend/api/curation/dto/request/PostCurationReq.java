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

    public Curation toEntity(PostCurationReq postCurationReq) {
        Curation curation = Curation.builder()
                .title(postCurationReq.getTitle())
                .list(postCurationReq.getList())
                .open(Boolean.TRUE)
                .build();

        return curation;
    }
}
