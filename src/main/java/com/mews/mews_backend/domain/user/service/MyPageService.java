package com.mews.mews_backend.domain.user.service;

import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.user.entity.User;
import com.mews.mews_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;

    //프로필 편집
    public void updateUser(Integer userId, UserDto.updateProfile profile){
        Optional<User> userResult = userRepository.findById(userId );

        userResult.ifPresent(user -> {
            //이름 바꾸기
            if(profile.getUserName()!=null){
                user.changeName(profile.getUserName());
            }
            //이미지 바꾸기
            if(profile.getImgUrl()!=null){
                user.changeImg(profile.getImgUrl());
            }
            //소개 바꾸기
            if(profile.getIntroduction()!=null){
                user.changeIntroduction(profile.getIntroduction());
            }
            //무조건 open 값 받아오기 - true:공개, false:비공개
            user.changeIsOpen(profile.isOpen());

            userRepository.save(user);
        });



    }
}
