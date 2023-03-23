package com.mews.mews_backend.domain.user.service;

import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.entity.User;
import com.mews.mews_backend.domain.user.entity.UserType;
import com.mews.mews_backend.domain.user.repository.UserRepository;
import com.mews.mews_backend.global.config.Jwt.RedisDao;
import com.mews.mews_backend.global.config.Jwt.TokenProvider;
import com.mews.mews_backend.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;

import static com.mews.mews_backend.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RedisDao redisDao;

    // 로그아웃
    public ResponseEntity<String> logout(String auth) {
        String atk = auth.substring(7);

        String email =  SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        if (redisDao.getValues(email) != null) {
            redisDao.deleteValues(email);
        }

        redisDao.setValues(atk, "logout", Duration.ofMillis(
                tokenProvider.getExpiration(atk)
        ));

        return new ResponseEntity<>("LOGOUT SEUCCESS", HttpStatus.OK);
    }

    //회원탈퇴
    public ResponseEntity<String> delete(String auth) {
        String atk = auth.substring(7);

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        //유저 상태 deleted
        User user = userRepository.findByUserEmail(email).orElseThrow();
        user.updateStatus();
        userRepository.save(user);

        //유저 토큰 값 없애기
        redisDao.deleteValues(email);

        return new ResponseEntity<>("USER STATUS DELETED", HttpStatus.OK);
    }
}
