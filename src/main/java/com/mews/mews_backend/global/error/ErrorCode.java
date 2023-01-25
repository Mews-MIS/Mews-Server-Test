package com.mews.mews_backend.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 * 에러 코드 관리
 */
@Getter
public enum ErrorCode {
    /*
     * 1000 : 요청 성공
     */
    SUCCESS(HttpStatus.OK, 1000, "요청에 성공하였습니다."),


    /*
     * 2000 : Request 오류
     */
    // Common
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, 2004,"사용자를 찾을 수 없습니다."),

    // users
    USERS_EMPTY_USER_NAME(HttpStatus.BAD_REQUEST, 2010, "유저 닉네임 값을 확인해주세요."),
    USERS_EMPTY_EMAIL(HttpStatus.BAD_REQUEST, 2011, "유저 이메일 값을 확인해주세요."),
    USERS_INVALID_EMAIL(HttpStatus.BAD_REQUEST, 2012, "잘못된 이메일 형식입니다."),
    USERS_EMPTY_IMG(HttpStatus.BAD_REQUEST, 2013, "유저 프로필 이미지 값을 확인해주세요."),


    // [POST] /users
    POST_USERS_EMPTY_CERTIFY_IMG(HttpStatus.BAD_REQUEST,2014,"소속 인증을 위한 이미지를 등록해주세요."),
    POST_USERS_EMPTY_EMAIL(HttpStatus.BAD_REQUEST, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(HttpStatus.BAD_REQUEST, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, 2017, "중복된 이메일입니다."),




    POST_USERS_EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, 2030, "비밀번호를 입력해주세요. "),
    POST_USERS_INVALID_PASSWORD(HttpStatus.BAD_REQUEST, 2031, "비밀번호가 일치하지 않습니다."),

    //session
    POST_SESSION_NOT_ADMIN(HttpStatus.BAD_REQUEST, 2040, "해당 유저가 그룹의 관리자가 아닙니다."),

    //auth

    PATCH_USER_CERTIFIED_FAIL(HttpStatus.BAD_REQUEST,2080,"certify실패"),
    CHECK_USER_CERTIFIED_FAIL(HttpStatus.BAD_REQUEST,2081,"certified 값 불러오기 실패 또는 유효하지 않은 값"),
    GET_UNCERTIFICATION_USER_FAIL(HttpStatus.BAD_REQUEST,2082,"미인증 유저 리스트 불러오기 실패"),
    ERROR_FIND_EMAIL(HttpStatus.BAD_REQUEST,2083,"가입하지 않은 이메일 입니다."),
    ERROR_FIND_USERIDX(HttpStatus.BAD_REQUEST,2084,"email로 가입된 userIdx가 존재하지 않음."),
    PATCH_USER_PASSWORD(HttpStatus.BAD_REQUEST,2085,"password patch실패"),
    NOT_LOGIN(HttpStatus.BAD_REQUEST,2086,"로그인 해주세요."),
    REFRESH_LOGOUT(HttpStatus.BAD_REQUEST,2087,"리프레시 토큰이 아닌 엑세스 토큰을 사용해주세요."),
    ALREADY_LOGOUT(HttpStatus.BAD_REQUEST,2088,"이미 로그아웃한 상태입니다."),


    /*
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(HttpStatus.BAD_REQUEST, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(HttpStatus.BAD_REQUEST, 3014, "없는 아이디거나 비밀번호가 틀렸습니다."),


    /*
     * 4000 : Database, Server 오류
     */
    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(HttpStatus.BAD_REQUEST, 4014, "유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(HttpStatus.BAD_REQUEST, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(HttpStatus.BAD_REQUEST, 4012, "비밀번호 복호화에 실패하였습니다."),


    // 5000 : 회차(SESSION)
    TEMP1(HttpStatus.BAD_REQUEST, 9000, "conflict 방지용 1"),
    START_TIME_ERROR(HttpStatus.BAD_REQUEST, 5000, "회차 시작 시간이 현재 시각보다 이전입니다."),
    NO_SESSION_INFO(HttpStatus.BAD_REQUEST, 5001, "해당 그룹의 세션 정보가 없거나 유효하지 않은 groupIdx 입니다."),
    INAPPROPRIATE_START_TIME(HttpStatus.BAD_REQUEST, 5002, "수정 요청된 시작시간이 현재 보다 이전입니다."),
    TIME_OVERLAPPED(HttpStatus.BAD_REQUEST, 5003, "다른 회차의 시간대와 겹칩니다."),
    ALREADY_DELETED_SESSION(HttpStatus.BAD_REQUEST, 5004, "해당 세션은 이미 삭제된 상태입니다."),
    DELETE_FAIL_SESSION(HttpStatus.BAD_REQUEST, 5005, "해당 세션은 삭제가 불가능합니다."),


    // 6000 : INVALID 오류
    TEMP2(HttpStatus.BAD_REQUEST, 9000, "conflict 방지용 2"),
    INVALID_USER(HttpStatus.BAD_REQUEST, 6000, "존재하지 않는 회원입니다."),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, 6001, "존재하지 않는 회원 닉네임입니다."),
    INVALID_GROUP(HttpStatus.BAD_REQUEST,6002,"존재하지 않는 그룹입니다." ),
    INVALID_ANNOUNCEMENT(HttpStatus.BAD_REQUEST,6003,"존재하지 않는 공지사항입니다."),
    INVALID_SESSION(HttpStatus.BAD_REQUEST, 6004, "세션 정보가 없습니다."),
    INVALID_ATTENDANCE(HttpStatus.BAD_REQUEST, 6005, "출석 정보가 없습니다."),
    NO_REGISTRATION_INFO(HttpStatus.BAD_REQUEST, 6006, "유저의 스터디 그룹 가입 정보가 없습니다."),
    NO_GROUP_ATTENDANCE(HttpStatus.BAD_REQUEST, 6007, "그룹의 출석 정보가 없습니다."),
    NO_GROUP_LEADER(HttpStatus.BAD_REQUEST,6008,"그룹 리더가 아니기 때문에, 권한이 없습니다."),
    NO_APPLICATION_INFO(HttpStatus.BAD_REQUEST, 6009, "유저의 지원서 정보가 없습니다."),
    INVALID_MEMO(HttpStatus.BAD_REQUEST, 6010, "메모 정보가 없습니다."),
    IOEXCEPTION(HttpStatus.BAD_REQUEST, 6011, "입출력 오류 발생"),




    // 7000 : 공지사항
    MODIFY_FAIL_ANNOUNCEMENT(HttpStatus.BAD_REQUEST, 7000, "수정에 실패하였습니다."),
    DELETE_FAIL_ANNOUNCEMENT(HttpStatus.BAD_REQUEST, 7002, "삭제에 실패하였습니다."),
    ALREADY_DELETED_ANNOUNCEMENT(HttpStatus.BAD_REQUEST, 7003, "해당 공지사항은 이미 삭제된 상태입니다."),
    TEMP3(HttpStatus.BAD_REQUEST, 9000, "conflict 방지용 3"),


    // 8000 : 출석, 메모
    ALREADY_ATTENDED(HttpStatus.BAD_REQUEST, 8000, "출석 상태가 결정되어 있습니다. 관리자만 변경 가능합니다."),
    FAIL_ATTEND(HttpStatus.BAD_REQUEST, 8001, "출석 가능한 시간이 아닙니다."),
    EXIST_ATTEND_INFO(HttpStatus.BAD_REQUEST, 8002, "이미 출석 데이터가 있습니다."),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, 8003, "메모가 이미 존재하여 생성할 수 없습니다."),

    TEMP4(HttpStatus.BAD_REQUEST, 9000, "conflict 방지용 4"),
    // 8050 : register
    FAIL_REGISTER_SAVE(HttpStatus.BAD_REQUEST,9051,"register 등록에 실패"),


    //9000 : 신청

    FULL_NUM_OF_Applicants(HttpStatus.BAD_REQUEST,9001,"신청 인원이 전부 다 찼습니다."),
    FAIL_SAVED_APPLICATION(HttpStatus.BAD_REQUEST,9002,"서버 error. 가입 신청 실패"),
    FAIL_CHANGED_STATUS(HttpStatus.BAD_REQUEST,9003,"상태 변경 실패"),
    DO_NOT_EXECUTE_CHANGE(HttpStatus.BAD_REQUEST,9004,"변경 실행안함 : 이미 변경되었거나 잘못된 조건"),
    INVALID_STATUS(HttpStatus.BAD_REQUEST,9005,"상태 변경 실패 : 유효하지 않은 값"),
    FAIL_REGISER(HttpStatus.BAD_REQUEST, 9006, "가입승인 -> Regiest 등록 실패"),

    //9050 : 그룹
    FAIL_LOAD_GROUPINFO(HttpStatus.BAD_REQUEST,9003,"스터디 그룹이 존재하지 않습니다."),
    FAIL_CLOSED_GROUPINFO(HttpStatus.BAD_REQUEST,9051,"종료된 스터디 그룹입니다."),
    INVAILD_ADMIN_APPLICATION(HttpStatus.BAD_REQUEST,9052,"그룹장은 가입이 불가합니다.");



    private final HttpStatus httpStatus;
    private final int code;
    private final String message;



    ErrorCode(HttpStatus httpStatus, int code, String message) { // BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}