package com.mews.mews_backend.domain.user.service;

import com.mews.mews_backend.api.user.dto.GoogleOauthToken;
import com.mews.mews_backend.api.user.dto.GoogleUser;
import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.entity.User;
import com.mews.mews_backend.domain.user.entity.UserType;
import com.mews.mews_backend.domain.user.repository.UserRepository;
import com.mews.mews_backend.domain.user.service.social.GoogleOauth;
import com.mews.mews_backend.global.config.Jwt.RedisDao;
import com.mews.mews_backend.global.config.Jwt.TokenProvider;
import com.mews.mews_backend.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

import static com.mews.mews_backend.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OauthService {

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final GoogleOauth googleOauth;

    private final HttpServletResponse response;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final RedisDao redisDao;

    //redirectURL 요청
    public void request(String socialLoginType) {
        String redirectURL = googleOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<UserDto.socialLoginResponse> Login(String name, String email, String img, int id) throws IOException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, "google");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("authenticated 되었나용?"+ authentication.isAuthenticated());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String atk = tokenProvider.createToken(authentication);
        String rtk = tokenProvider.createRefreshToken(email);
        redisDao.setValues(email, rtk, Duration.ofDays(14));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + atk);

        return new ResponseEntity<>(UserDto.socialLoginResponse.response(
               name, email, img, id, atk, rtk
        ), HttpStatus.OK);

    }

    public ResponseEntity<String> requestAccessToken(String code) {
        return googleOauth.requestAccessToken(code);
    }

    public ResponseEntity<UserDto.socialLoginResponse> oauthLogin(String socialLoginType, String code) throws IOException {
        //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
        ResponseEntity<String> accessTokenResponse = this.requestAccessToken(code);
        //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
        GoogleOauthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

        //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
        ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(oAuthToken);
        //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
        GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);

        String email = googleUser.getEmail();
        String name = googleUser.getName();
        String img = googleUser.getPicture();

        Optional<User> findUser = userRepository.findByUserEmail(email);
        // 첫 로그인시 default 값으로 회원가입
        if (findUser.isEmpty()) {
            log.info("새 유저 회원가입");
            User newUser = User.builder()
                    .userName(name)
                    .userEmail(email)
                    .imgUrl(img)
                    .introduction("")
                    .likeCount(0)
                    .bookmarkCount(0)
                    .subscribeCount(0)
                    .userType(UserType.ROLE_USER)
                    .isOpen(true)
                    .social(passwordEncoder.encode("google"))
                    .status("ACTIVE")
                    .build();

            Integer id = userRepository.save(newUser).getId();

            return Login(newUser.getUserName(),newUser.getUserEmail(), newUser.getImgUrl(), id);

        } else { //이메일이 존재할 시 바로 로그인
            log.info("이메일 존재함");
            // 이메일이 존재할시 로그인
            User user = findUser.orElseThrow();
            return Login(name, email, img, user.getId());
        }
    }

    public void REGISTER_VALIDATION(UserDto.register request) {

        //1. 닉네임 null 체크
        if(request.getUserName()==null){
            throw new BaseException(EMPTY_USERNAME);
        }

        //2. 이메일 null 체크
        if(request.getUserEmail()==null){
            throw new BaseException(EMPTY_EMAIL);
        }

        //2-2. 이메일 형식 체크
        if(!request.getUserEmail().contains("@")){
            throw new BaseException(INVALID_EMAIL);
        }

        //2-3. 이메일 중복 체크
        if(userRepository.existsByUserEmail(request.getUserEmail())) {
            throw new BaseException(USER_EMAIL_EXISTS);
        }

        //3. 프로필 이미지
        if(request.getImgUrl()==null) {
            throw new BaseException(EMPTY_IMG);
        }
    }


    public ResponseEntity<UserDto.socialLoginResponse> socialRegister(UserDto.register request) throws IOException {
        log.info("service 안에 들어옴");

        //validation 처리
        REGISTER_VALIDATION(request);

        User user = User.builder()
                .userName(request.getUserName())
                .userEmail(request.getUserEmail())
                .imgUrl(request.getImgUrl())
                .introduction(request.getIntroduction())
                .likeCount(0)
                .bookmarkCount(0)
                .subscribeCount(0)
                .userType(UserType.ROLE_USER)
                .isOpen(true)
                .social(passwordEncoder.encode("google"))
                .status("ACTIVE")
                .build();

        Integer id = userRepository.save(user).getId();

        return Login(user.getUserName(),user.getUserEmail(), user.getImgUrl(), id);
    }

    //accessToken 재발급
    @Transactional
    public ResponseEntity<UserDto.tokenResponse> reissue(String rtk) {
        String username = tokenProvider.getRefreshTokenInfo(rtk);
        String rtkInRedis = redisDao.getValues(username);

        if (Objects.isNull(rtkInRedis) || !rtkInRedis.equals(rtk))
            throw new BaseException(REFRESH_TOKEN_BAD_REQUEST); // 410

        return new ResponseEntity<>(UserDto.tokenResponse.response(
                tokenProvider.reCreateToken(username),
                null
        ), HttpStatus.OK);
    }


}
