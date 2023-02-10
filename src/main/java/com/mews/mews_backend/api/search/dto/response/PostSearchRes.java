package com.mews.mews_backend.api.search.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PostSearchRes {

    private List<PostArticleRes> postArticleRes;

    private List<PostEditorRes> postEditorRes;

    // to DTO
    public PostSearchRes(List<PostArticleRes> postArticleRes, List<PostEditorRes> postEditorRes) {
        this.postArticleRes = postArticleRes;
        this.postEditorRes = postEditorRes;
    }
}
