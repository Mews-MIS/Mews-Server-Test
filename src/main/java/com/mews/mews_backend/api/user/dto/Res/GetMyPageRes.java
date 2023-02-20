package com.mews.mews_backend.api.user.dto.Res;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMyPageRes {

    private  String imgUrl;

    private  String userName;

    private  String introduction;

    private  int likeCount;

    private  int bookmarkCount;

    private  int subscribeCount;

}
