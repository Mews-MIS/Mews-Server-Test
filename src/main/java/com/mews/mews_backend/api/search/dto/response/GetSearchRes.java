package com.mews.mews_backend.api.search.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class GetSearchRes {

    private List<GetArticleRes> getArticleRes;

    private List<GetEditorRes> getEditorRes;

    // to DTO
    public GetSearchRes(List<GetArticleRes> getArticleRes, List<GetEditorRes> getEditorRes) {
        this.getArticleRes = getArticleRes;
        this.getEditorRes = getEditorRes;
    }
}
