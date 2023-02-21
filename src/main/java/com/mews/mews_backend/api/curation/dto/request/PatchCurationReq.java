package com.mews.mews_backend.api.curation.dto.request;

import com.mews.mews_backend.domain.curation.entity.Curation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatchCurationReq {

    @NotNull
    @ApiModelProperty(notes = "큐레이션 id")
    private Integer id;

    @ApiModelProperty(notes = "큐레이션 제목")
    private String title;

    @ApiModelProperty(notes = "아티클 리스트")
    private List<String> list;

    @ApiModelProperty(notes = "공개 여부")
    private Boolean open;
}
