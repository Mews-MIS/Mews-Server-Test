package com.mews.mews_backend.api.editor.dto.response;

import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.Getter;

@Getter
public class GetEditorSubRes {

    private Integer id;

    private String name;

    private String imgUrl;

    private String introduction;

    private Boolean isSubscribed;

    // Entity to DTO
    public GetEditorSubRes(Editor editor, Boolean checkSub) {
        this.id = editor.getId();
        this.name = editor.getName();
        this.imgUrl = editor.getImgUrl();
        this.introduction = editor.getIntroduction();
        this.isSubscribed = checkSub;
    }
}
