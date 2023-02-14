package com.mews.mews_backend.api.search.dto.response;

import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.Getter;

@Getter
public class PostEditorRes {

    private Integer id;

    private String name;

    private String imgUrl;

    //Entity to DTO
    public PostEditorRes(Editor editor) {
        this.id = editor.getId();
        this.name = editor.getName();
        this.imgUrl = editor.getImgUrl();
    }
}
