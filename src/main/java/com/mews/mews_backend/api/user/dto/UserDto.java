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
        private final int userId;
        private final String atk;
        private final String rtk;

        public static socialLoginResponse response(String userName, String userEmail, String imgUrl, int id, String atk, String rtk) {
            return socialLoginResponse.builder()
                    .userName(userName)
                    .userEmail(userEmail)
                    .imgUrl(imgUrl)
                    .userId(id)
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
        private String userName;
        private String userEmail;
        private String imgUrl;
        private String introduction;
        private boolean isOpen;
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