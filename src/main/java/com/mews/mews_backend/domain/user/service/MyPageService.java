package com.mews.mews_backend.domain.user.service;

import com.mews.mews_backend.api.user.dto.GetMyPageBookmarkReq;
import com.mews.mews_backend.api.user.dto.UserDto;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.user.entity.Bookmark;
import com.mews.mews_backend.domain.user.entity.User;
import com.mews.mews_backend.domain.user.repository.BookmarkRepository;
import com.mews.mews_backend.domain.user.repository.UserRepository;
import com.mews.mews_backend.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mews.mews_backend.global.error.ErrorCode.NOT_AUTHENTICATED_USER;
import static com.mews.mews_backend.global.error.ErrorCode.USER_BOOKMARK_EXISTS;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ArticleRepository articleRepository;

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

    public void USER_VALIDATION(Integer userId){
        //토큰 값의 유저와 userId의 유저가 일치하는지
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equals(userRepository.findById(userId).orElseThrow().getUserEmail())) {
            throw new BaseException(NOT_AUTHENTICATED_USER);
        }
    }

    public void USER_BOOKMARK_VALIDATION(Integer userId, Integer articleId){
        List<Bookmark> bookmarkValidation = bookmarkRepository.existsByIdAndArticleId(userId, articleId);
        if(!bookmarkValidation.isEmpty()){
            throw new BaseException(USER_BOOKMARK_EXISTS);
        }
    }

    //북마크 추가
    public void insertBookmark(Integer userId, Integer articleId) {
        log.info("=======예외 처리======");
        //예외 처리 : 토큰 값의 유저와 userId 값이 일치하지 않으면 예외 발생
        USER_VALIDATION(userId);
        //예외 처리 : 이미 북마크된 아티클이면 예외 발생
        USER_BOOKMARK_VALIDATION(userId, articleId);

        //북마크 추가
        User user = userRepository.findById(userId).orElseThrow();
        Article article = articleRepository.findById(articleId).orElseThrow();

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .article(article)
                .build();

        bookmarkRepository.save(bookmark);

        //user bookmarkcnt +1증가
        user.updateBookmark();
        userRepository.save(user);
    }

    //내 북마크 글 가져오기
    public List<GetMyPageBookmarkReq> getMyBookmark(Integer userId){
        List<Bookmark> findMyBookmark = bookmarkRepository.findAllByUserId(userId);
        List<GetMyPageBookmarkReq> getMyPageBookmarkReqs = new ArrayList<>();

        for(Bookmark bookmark : findMyBookmark){
            GetMyPageBookmarkReq dto = GetMyPageBookmarkReq.builder()
                    .id(bookmark.getArticle().getArticle_id())
                    .title(bookmark.getArticle().getTitle())
                    .likeCount(bookmark.getArticle().getLike_count())
                    .editors("일단 X")
                    .img("일단 X")
                    .build();
            getMyPageBookmarkReqs.add(dto);
        }
        return getMyPageBookmarkReqs;
    }
}
