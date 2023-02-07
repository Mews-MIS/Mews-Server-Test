package com.mews.mews_backend.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMyPageBookmarkRes {
    //최신순
    //id
    private Integer id;

    //제목
    private String title;

    //좋아요수
    private Integer likeCount;

    //필진 이름
    private String editors;

    //이미지
    private String img;

}
