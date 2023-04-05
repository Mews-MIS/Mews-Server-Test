package com.mews.mews_backend.domain.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mews.mews_backend.api.user.dto.Req.PatchUserProfileReq;
import com.mews.mews_backend.api.user.dto.Res.GetMyPageArticleRes;
import com.mews.mews_backend.api.user.dto.Res.GetMyPageRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.entity.ArticleAndEditor;
import com.mews.mews_backend.domain.article.repository.ArticleAndEditorRepository;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.editor.entity.Editor;
import com.mews.mews_backend.domain.editor.repository.EditorRepository;
import com.mews.mews_backend.domain.user.entity.Bookmark;
import com.mews.mews_backend.domain.user.entity.Like;
import com.mews.mews_backend.domain.user.entity.Subscribe;
import com.mews.mews_backend.domain.user.entity.User;
import com.mews.mews_backend.domain.user.repository.BookmarkRepository;
import com.mews.mews_backend.domain.user.repository.LikeRepository;
import com.mews.mews_backend.domain.user.repository.SubscribeRepository;
import com.mews.mews_backend.domain.user.repository.UserRepository;
import com.mews.mews_backend.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mews.mews_backend.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final SubscribeRepository subscribeRepository;
    private final EditorRepository editorRepository;
    private final LikeRepository likeRepository;
    private final AmazonS3Client amazonS3Client;
    private final ArticleAndEditorRepository articleAndEditorRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    //로그인 상태에서 - 프로필
    public GetMyPageRes getUserInfo(Integer userId) {
        //(1) 유저 정보 가져오기
        User user = getSecurityContextUser();

        //(2)-1 내 정보 반환
        if (user.getId() == userId) { return GetMyPageRes.response(user);}
        else {  //(2)-2 타인정보 반환
            user = userRepository.findById(userId).orElseThrow();
            if (user.isOpen() == false) {
                throw new BaseException(PROFILE_NOT_OPEN);
            } else { return GetMyPageRes.response(user); }
        }
    }

    //프로필 편집
    public void updateUserInfo(Integer userId, PatchUserProfileReq profile, MultipartFile multipartFile){
        User user = USER_VALIDATION(userId);

        //(1) 유저 이름 변경
        if(profile.getUserName()!=null){user.changeName(profile.getUserName());}

        //(2) 유저 프로필 이미지 변경
        if(multipartFile != null && !multipartFile.isEmpty()){
            String img = null;
            try {
                img = updateImage(multipartFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
                user.changeImg(img);
            }

        //(3) 유저 자기소개 변경
        if(profile.getIntroduction()!=null){
            user.changeIntroduction(profile.getIntroduction());
        }

        //무조건 open 값 받아오기 - true:공개, false:비공개
        user.changeIsOpen(profile.isOpen());

        userRepository.save(user);
    };

    //이미지 변경
    public String updateImage(MultipartFile multipartFile) throws IOException {
        LocalDate now = LocalDate.now();
        String uuid = UUID.randomUUID()+toString();
        String fileName = uuid+"_"+multipartFile.getOriginalFilename();
        String userImg = "user/" + now+"/"+ fileName;
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3Client.putObject(new PutObjectRequest(bucket, userImg, multipartFile.getInputStream(), objMeta)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        String img = amazonS3Client.getUrl(bucket, fileName).toString();

        return img;
    }


    //SecurityContext에 등록된 유저정보 가져오기
    public User getSecurityContextUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUserEmail(authentication.getName()).orElseThrow();
    }

    //토큰 값의 유저와 userId의 유저가 일치하는지
    public User USER_VALIDATION(Integer userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findById(userId).orElseThrow();
        if (!authentication.getName().equals(user.getUserEmail())) {
            throw new BaseException(NOT_AUTHENTICATED_USER);
        }

        return user;
    }


    //북마크 글 가져오기
    public List<GetMyPageArticleRes> getMyBookmark(Integer userId){
        //(1) 유저 정보 가져오기
        User user = getSecurityContextUser();

        //(2) 북마크 정보 가져오기
        List<Bookmark> findMyBookmark = new ArrayList<>() ;
        List<GetMyPageArticleRes> getMyPageBookmarkRes = new ArrayList<>();

        if(user.getId() == userId){ //내 계정
            findMyBookmark = bookmarkRepository.findAllByUserOrderByModifiedAtDesc(user);
        } else { //타인 계정
            user = userRepository.findById(userId).orElseThrow();
            if(user.isOpen()==false){
                throw new BaseException(PROFILE_NOT_OPEN);
            } else {
                findMyBookmark = bookmarkRepository.findAllByUserOrderByModifiedAtDesc(user);
            }
        }

        for(Bookmark bookmark : findMyBookmark){
            List<String> editors = editorToString(bookmark.getArticle());
            GetMyPageArticleRes dto = GetMyPageArticleRes.builder()
                    .id(bookmark.getArticle().getId())
                    .type(bookmark.getArticle().getType())
                    .title(bookmark.getArticle().getTitle())
                    .likeCount(bookmark.getArticle().getLike_count())
                    .editors(editors)
                    .img(bookmark.getArticle().getFileUrls())
                    .isBookmarked(bookmarkRepository.existsByUserAndArticle(user,bookmark.getArticle()))
                    .isLiked(likeRepository.existsByArticleAndUser(bookmark.getArticle(), user))
                    .build();
            getMyPageBookmarkRes.add(dto);
        }
        return getMyPageBookmarkRes;
    }

    // 좋아요 글 가져오기
    public List<GetMyPageArticleRes> getLikeArticle(Integer userId){
        //(1) 유저 정보 가져오기
        User user = getSecurityContextUser();

        //(2) 좋아요 글 가져오기
        List<Like> findAllLike = new ArrayList<>();
        List<GetMyPageArticleRes> getMyPageLikeRes = new ArrayList<>();

        if(user.getId() == userId){ //(2)-1 내 좋아요 글
            findAllLike = likeRepository.findAllByUserOrderByModifiedAtDesc(user);
        }else {
            user = userRepository.findById(userId).orElseThrow();
            if(user.isOpen()==false){//(2)-2 타인 계정 글
                throw new BaseException(PROFILE_NOT_OPEN);
            } else{
                findAllLike = likeRepository.findAllByUserOrderByModifiedAtDesc(user);
            }
        }

        for(Like likeArticle : findAllLike){
            List<String> editors = editorToString(likeArticle.getArticle());
            GetMyPageArticleRes dto = GetMyPageArticleRes.builder()
                    .id(likeArticle.getArticle().getId())
                    .type(likeArticle.getArticle().getType())
                    .title(likeArticle.getArticle().getTitle())
                    .likeCount(likeArticle.getArticle().getLike_count())
                    .editors(editors)
                    .img(likeArticle.getArticle().getFileUrls())
                    .isBookmarked(bookmarkRepository.existsByUserAndArticle(user, likeArticle.getArticle()))
                    .isLiked(likeRepository.existsByArticleAndUser(likeArticle.getArticle(), user))
                    .build();
            getMyPageLikeRes.add(dto);
        }
        return getMyPageLikeRes;
    }


    //게시글 필진 String 값으로 반환
    public List<String> editorToString(Article article){
        List<ArticleAndEditor> findAllEditors = articleAndEditorRepository.findAllByArticle(article);
        List<String> editors = new ArrayList<>();
        for(int i=0; i<findAllEditors.size();i++){
            editors.add(findAllEditors.get(i).getEditor().getName());
        }
        return editors;
    }

    //필진 구독하기
    public String insertEditor(Integer userId, Integer editorId){
        User user = getSecurityContextUser();

        boolean isSubscribed = subscribeRepository.existsByEditorIdAndUserId(editorId,userId);
        Editor editor = editorRepository.findById(editorId).orElseThrow();

        if(isSubscribed == false){
            //유저가 구독한 editor 저장
            subscribeRepository.save(Subscribe.createSubscribe(user, editor));
            //user subscribecnt ++
            user.upSubscribe();
            userRepository.save(user);

            return "ADD SUBSCRIBE";
        } else {
            //유저가 구독한 editor 삭제
            subscribeRepository.deleteByEditorIdAndUserId(editorId, userId);
            //유저가 구독한 editor 취소
            user.downSubscribe();
            userRepository.save(user);

            return "DELETE SUBSCRIBE";
        }

    }


    //필진 글 보여주기
    public List<GetMyPageArticleRes> getEditorArticles(Integer userId, Integer editorId) {
        //(1) 유저 정보 가져오기
        User user = USER_VALIDATION(userId);

        //(2) 에디터 글 가져오기
        Editor editor = editorRepository.findById(editorId).orElseThrow();
        List<ArticleAndEditor> findAllArticle = articleAndEditorRepository.findAllByEditorOrderByModifiedAt(editor);
        List<GetMyPageArticleRes> getEditorArticleRes = new ArrayList<>();

        for(ArticleAndEditor editorArticle : findAllArticle){
            List<String> editors = editorToString(editorArticle.getArticle());
            GetMyPageArticleRes dto = GetMyPageArticleRes.builder()
                    .id(editorArticle.getArticle().getId())
                    .type(editorArticle.getArticle().getType())
                    .title(editorArticle.getArticle().getTitle())
                    .likeCount(editorArticle.getArticle().getLike_count())
                    .editors(editors)
                    .img(editorArticle.getArticle().getFileUrls())
                    .isBookmarked(bookmarkRepository.existsByUserAndArticle(user, editorArticle.getArticle()))
                    .isLiked(likeRepository.existsByArticleAndUser(editorArticle.getArticle(), user))
                    .build();
            getEditorArticleRes.add(dto);
        }
        return getEditorArticleRes;
    }

    // 구독한 필진 정보 불러오긱
    public List<Editor> getSubscribeEditorList(Integer id) {
        List<Editor> editors = editorRepository.findAllByUserId(id);

        return editors;
    }
}
