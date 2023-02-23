package com.mews.mews_backend.api.curation.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllCurationRes {

    @ApiModelProperty(notes = "배치된 큐레이션")
    private List<GetCurationTitleRes> checked = new ArrayList<>();

    @ApiModelProperty(notes = "모든 큐레이션")
    private List<GetCurationTitleRes> allCuration = new ArrayList<>();
}
