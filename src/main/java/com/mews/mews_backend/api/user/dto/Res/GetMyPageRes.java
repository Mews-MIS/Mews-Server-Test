package com.mews.mews_backend.api.user.dto.Res;

import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.entity.User;
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

    private boolean isOpen;

    public static GetMyPageRes response(User user) {
        return GetMyPageRes.builder()
                .imgUrl(user.getImgUrl())
                .userName(user.getUserName())
                .introduction(user.getIntroduction())
                .bookmarkCount(user.getBookmarkCount())
                .likeCount(user.getLikeCount())
                .subscribeCount(user.getSubscribeCount())
                .isOpen(user.isOpen())
                .build();
    }

}
