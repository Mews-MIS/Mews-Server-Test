package com.mews.mews_backend.api.editor.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatchEditorReq {

    @NotNull
    @ApiModelProperty(notes = "필진 id", example = "1")
    private Integer id;

    @ApiModelProperty(notes = "이름", example = "이정진")
    private String name;

    @ApiModelProperty(notes = "자기 소개", example = "동국대학교 경영정보학과")
    private String introduction;
}
