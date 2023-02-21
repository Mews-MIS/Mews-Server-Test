package com.mews.mews_backend.api.user.dto.Req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostAdminLoginReq {

    @ApiModelProperty(notes = "유저 아이디")
    private String userId;

    @ApiModelProperty(notes = "유저 비밀번호")
    private String userPassword;
}
