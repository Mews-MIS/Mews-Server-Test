package com.mews.mews_backend.api.user.dto.Req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchUserProfileReq {

    private String userName; //닉네임
    private String userEmail;
    private String introduction; //자기소개
    private boolean isOpen; //공개 비공개 여부

}
