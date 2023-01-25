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
    LOGIN_FALSE("아이디 또는 비밀번호를 잘못 입력하였습니다."),
    NOT_SOCIAL_LOGIN("소셜 아이디로 회원가입된 유저입니다."),

    //회원가입
    REGISTER_INFO_NULL("필수 항목을 입력하지 않았습니다."),
    PASSWORD_SIZE_ERROR("비밀번호가 6자리 이상이여야 합니다."),
    NOT_EMAIL_FORM("이메일 형식이 아닙니다."),
    NOT_CONTAINS_EXCLAMATIONMARK("비밀번호에 특수문자가 포함되어있지 않습니다."),
    DUPLICATE_USER("해당 이메일의 가입자가 이미 존재합니다."),
    WANT_SOCIAL_REGISTER("해당 이메일은 소셜로그인으로 진행해야 합니다."),

    OFFICE_ADD_OR_MAKET_ADD_IS_REQUIRED("기업 회원가입은 사업자 등록증과 플리마켓 허가증은 필수입니다."),

    //회원탈퇴
    USER_DELETE_STATUS_FALSE( "비밀번호가 일치하지 않아 탈퇴에 실패했습니다." ),

    //찜
    ALREADY_LIKED("이미 찜한 플리마켓 입니다."),

    // 알수 없는 오류의 처리
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다."),

    // 신청서
    APPLICATION_NO_EXIST("존재하지 않는 신청서입니다."),
    NO_AUTHORITY("접근 권한이 없습니다."),

    // 플리마켓
    MARKET_NOT_EXIST("존재하지 않는 플리마켓입니다."),

    POST_NOT_FOUND("게시물을 찾을 수 없습니다."),

    //게시판
    POST_NO_EXIST("존재하지 않는 게시글입니다.");
    private final String statusMessage;

}
