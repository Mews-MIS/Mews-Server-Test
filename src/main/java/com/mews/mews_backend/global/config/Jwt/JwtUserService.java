package com.mews.mews_backend.global.config.Jwt;

import com.mews.mews_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userEmail)  {
        return userRepository.findByUserEmail(userEmail)
                .map(this::createUser)
                .orElseThrow(()-> new UsernameNotFoundException(userEmail + "-> 데이터베이스에서 찾을 수 없습니다."));
    }


    private UserDetails createUser(com.mews.mews_backend.domain.user.entity.User user){
        log.info("createUser 실행됨1");
        GrantedAuthority grantedAuthority =
                new SimpleGrantedAuthority(user.getUserType().toString());
        log.info("createUser 실행됨2");
        return new User(user.getUserEmail(),user.getSocial(), Collections.singleton(grantedAuthority));
    }

}