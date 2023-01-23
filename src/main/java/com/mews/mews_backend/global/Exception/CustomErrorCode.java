package com.mews.mews_backend.global.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor // enum에 하나의 프로퍼티가 있는데 프로퍼티를 사용해서 enum을 하나 생성해줄때 메세지를 넣어서 생성해주는 생성자가 없어서 선언해줘야함
@Getter
public enum CustomErrorCode {

    //로그인 & 로그아웃 검증
    JWT_CREDENTIALS_STATUS_FALSE("로그인이 유효하지 않습니다."),
    JWT_TIMEOUT( "만료된 JWT 토큰입니다."),
    REFRESH_TOKEN_IS_BAD_REQUEST("잘못된 RefreshToken 입니다 : null 이거나  not equals"),
    NOT_SOCIAL_LOGIN("소셜 아이디로 회원가입된 유저입니다.");

    private final String statusMessage;

}

