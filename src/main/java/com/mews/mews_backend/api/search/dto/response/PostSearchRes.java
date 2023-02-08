package com.mews.mews_backend.api.search.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PostSearchRes {

    private List<PostArticleRes> postArticleRes;

    private List<PostSearchRes> postSearchRes;
}
