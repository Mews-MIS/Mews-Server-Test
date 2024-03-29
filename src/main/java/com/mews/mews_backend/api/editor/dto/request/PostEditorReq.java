package com.mews.mews_backend.api.editor.dto.request;

import com.mews.mews_backend.domain.editor.entity.Editor;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostEditorReq {

    @NotBlank
    @ApiModelProperty(notes = "필진 이름", example = "이정진")
    private String name;

    @ApiModelProperty(notes = "필진 자기소개", example = "")
    private String introduction;

    //DTO to Entity
    public Editor toEntity(PostEditorReq postEditorReq, String img) {
        Editor editor = Editor.builder()
                .name(postEditorReq.getName())
                .imgUrl(img)
                .introduction(postEditorReq.getIntroduction())
                .build();

        return editor;
    }
}
