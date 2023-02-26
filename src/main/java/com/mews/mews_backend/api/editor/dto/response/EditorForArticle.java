package com.mews.mews_backend.api.editor.dto.response;

import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.Getter;

@Getter
public class EditorForArticle {
    // Article을 작성한 Editor 정보를 위한 DTO

    private Integer id;

    private String name;

    public EditorForArticle(Editor editor) {
        this.id = editor.getId();
        this.name = editor.getName();
    }
}
