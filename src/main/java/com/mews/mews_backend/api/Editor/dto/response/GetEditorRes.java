package com.mews.mews_backend.api.Editor.dto.response;

import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.Getter;

@Getter
public class GetEditorRes {

    private Integer id;

    private String name;

    private String imgUrl;

    private String introduction;

    // Entity to DTO
    public GetEditorRes(Editor editor) {
        this.id = editor.getId();
        this.name = editor.getName();
        this.imgUrl = editor.getImgUrl();
        this.introduction = editor.getIntroduction();
    }
}
