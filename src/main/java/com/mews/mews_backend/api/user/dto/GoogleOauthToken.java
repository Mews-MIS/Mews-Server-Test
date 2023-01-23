package com.mews.mews_backend.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoogleOauthToken {
    private String access_token;
    private int expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}