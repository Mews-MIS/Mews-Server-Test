package com.mews.mews_backend.api.user.dto.Res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMyPageArticleRes {
    //id
    private Integer id;

    //제목
    private String title;

    //좋아요수
    private Integer likeCount;

    //필진 이름
    private List<String> editors;

    //이미지
    private List<String> img;

    //북마크 여부
    private boolean isBookmarked;

    //좋아요 여부
    private boolean isLiked;
}
