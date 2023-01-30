package com.mews.mews_backend.api.user.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetMyPageReq {

    private  String imgUrl;

    private  String userName;

    private  String introduction;

    private  int likeCount;

    private  int bookmarkCount;

    private  int subscribeCount;

    //리스트 형태로 저장
    private GetMyPageBookmarkReq req;

}
