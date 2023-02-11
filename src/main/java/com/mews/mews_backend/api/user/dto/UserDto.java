package com.mews.mews_backend.api.user.dto;

import lombok.*;

import java.io.Serializable;

public class UserDto implements Serializable {

    @Data
    @Builder
    public static class socialLoginResponse {
        private final String userName;
        private final String userEmail;
        private final String imgUrl;
        private final int id;
        private final String atk;
        private final String rtk;

        public static socialLoginResponse response(String userName, String userEmail, String imgUrl,int id,  String atk, String rtk) {
            return socialLoginResponse.builder()
                    .userName(userName)
                    .userEmail(userEmail)
                    .imgUrl(imgUrl)
                    .id(id)
                    .atk(atk)
                    .rtk(rtk)
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class register {
        private String userName; //닉네임
        private String userEmail; //이메일-무조건 있어야 DB에서 구별가능
        private String imgUrl; //프로필 이미지
        private String introduction; //자기소개
        private boolean isOpen; //공개 비공개 여부
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class updateProfile {
        private String userName; //닉네임
        private String userEmail;
        private String introduction; //자기소개
        private boolean isOpen; //공개 비공개 여부
    }

    @Data
    @Builder
    public static class tokenResponse {
        private final String atk;
        private final String rtk;

        public static tokenResponse response(String atk, String rtk) {
            return tokenResponse.builder()
                    .atk(atk)
                    .rtk(rtk)
                    .build();
        }
    }
}