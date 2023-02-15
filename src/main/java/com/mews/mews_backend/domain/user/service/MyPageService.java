package com.mews.mews_backend.domain.user.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
    private final ArticleRepository articleRepository;

    private final SubscribeRepository subscribeRepository;
    private final EditorRepository editorRepository;
    private final LikeRepository likeRepository;
    private final AmazonS3Client amazonS3Client;
    private final ArticleAndEditorRepository articleAndEditorRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    //프로필
    public GetMyPageRes getUserInfo(Integer userId){
        User user = USER_VALIDATION(userId);

        GetMyPageRes userDto = GetMyPageRes.builder()
                .imgUrl(user.getImgUrl())
                .userName(user.getUserName())
                .introduction(user.getIntroduction())
                .bookmarkCount(user.getBookmarkCount())
                .likeCount(user.getLikeCount())
                .subscribeCount(user.getSubscribeCount())
                .build();

        return userDto;
    }

    //프로필 편집
    public void updateUserInfo(Integer userId, PatchUserProfileReq profile, MultipartFile multipartFile){
        User user = USER_VALIDATION(userId);

        //이름 바꾸기
        if(profile.getUserName()!=null){
            user.changeName(profile.getUserName());
        }
        //이미지 바꾸기
        if(multipartFile != null && !multipartFile.isEmpty()){
            String img = null;
            try {
                img = updateImage(multipartFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
                user.changeImg(img);
            }

        //소개 바꾸기
        if(profile.getIntroduction()!=null){
            user.changeIntroduction(profile.getIntroduction());
        }

        //무조건 open 값 받아오기 - true:공개, false:비공개
        user.changeIsOpen(profile.isOpen());

        userRepository.save(user);
    };

    //이미지 넣기
    public String updateImage(MultipartFile multipartFile) throws IOException {
        LocalDate now = LocalDate.now();
        String uuid = UUID.randomUUID()+toString();
        String fileName = uuid+"_"+multipartFile.getOriginalFilename();
        String userImg = "user/" + now+"/"+ fileName;
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3Client.putObject(bucket, userImg, multipartFile.getInputStream(), objMeta);

        String img = amazonS3Client.getUrl(bucket, fileName).toString();

        return img;
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


    //북마크 추가
    public boolean insertBookmark(Integer userId, Integer articleId) {
        User user = USER_VALIDATION(userId);

        Article article = articleRepository.findById(articleId).orElseThrow();
        boolean bookmarkValidation = bookmarkRepository.existsByUserAndArticle(user, article);

        //북마크 안 되어 있는 글 => 북마크 추가
        if(bookmarkValidation == false){
            Bookmark bookmark = Bookmark.builder()
                    .user(user)
                    .article(article)
                    .build();

            bookmarkRepository.save(bookmark);

            //user bookmarkcnt +1증가
            user.upBookmark();
            userRepository.save(user);

            return true;
        } else {
            //북마크 삭제
            bookmarkRepository.deleteByIdAndArticleId(userId, articleId);
            //북마크cnt --
            user.downBookmark();
            userRepository.save(user);

            return false;
        }


    }

    //내 북마크 글 가져오기
    public List<GetMyPageArticleRes> getMyBookmark(Integer userId){
        User user = USER_VALIDATION(userId);

        List<Bookmark> findMyBookmark = bookmarkRepository.findAllByUserOrderByModifiedAtDesc(user);
        List<GetMyPageArticleRes> getMyPageBookmarkRes = new ArrayList<>();

        for(Bookmark bookmark : findMyBookmark){
            List<String> editors = editorToString(bookmark.getArticle());
            GetMyPageArticleRes dto = GetMyPageArticleRes.builder()
                    .id(bookmark.getArticle().getId())
                    .title(bookmark.getArticle().getTitle())
                    .likeCount(bookmark.getArticle().getLike_count())
                    .editors(editors)
                    .img(bookmark.getArticle().getFileUrls())
                    .isBookmarked(true)
                    .isLiked(likeRepository.existsByArticleAndUser(bookmark.getArticle(), user))
                    .build();
            getMyPageBookmarkRes.add(dto);
        }
        return getMyPageBookmarkRes;
    }

    //내 좋아요 글 가져오기
    public List<GetMyPageArticleRes> getLikeArticle(Integer userId){
        User user = USER_VALIDATION(userId);

        List<Like> findAllLike = likeRepository.findAllByUserOrderByModifiedAtDesc(user);
        List<GetMyPageArticleRes> getMyPageLikeRes = new ArrayList<>();

        for(Like likeArticle : findAllLike){
            List<String> editors = editorToString(likeArticle.getArticle());
            GetMyPageArticleRes dto = GetMyPageArticleRes.builder()
                    .id(likeArticle.getArticle().getId())
                    .title(likeArticle.getArticle().getTitle())
                    .likeCount(likeArticle.getArticle().getLike_count())
                    .editors(editors)
                    .img(likeArticle.getArticle().getFileUrls())
                    .isBookmarked(bookmarkRepository.existsByUserAndArticle(user, likeArticle.getArticle()))
                    .isLiked(true)
                    .build();
            getMyPageLikeRes.add(dto);
        }
        return getMyPageLikeRes;
    }

    //좋아요
    public boolean insertlikeArticle(Integer userId, Integer articleId){
        User user = USER_VALIDATION(userId);

        Article article = articleRepository.findById(articleId).orElseThrow();
        boolean likeValidation = likeRepository.existsByArticleAndUser(article, user);


        //좋아요 한 적 없는 글 => 좋아요 추가
        if(likeValidation == false){

            Like like = Like.builder()
                    .user(user)
                    .article(article)
                    .build();

            likeRepository.save(like);

            //user likecnt +1증가
            user.upLike();
            userRepository.save(user);

            //article likecnt +1증가
            article.upLike();
            articleRepository.save(article);

            return true;
        } else {     //좋아요 이미 되어 있는 글 => 좋아요 취소
            //좋아요 삭제
            likeRepository.deleteByIdAndArticleId(userId, articleId);

            //user likecnt --
            user.downLike();
            userRepository.save(user);

            //article likecnt --
            article.downLike();
            articleRepository.save(article);
            return false;
        }
    }

    //필진 구독하기
    public void insertEditor(Integer userId, Integer editorId){
        User user = USER_VALIDATION(userId);
        Editor editor = editorRepository.findById(editorId).orElseThrow();

        Subscribe subscribe = Subscribe.builder()
                .editor(editor)
                .user(user)
                .build();

        subscribeRepository.save(subscribe);
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

    //필진 글 보여주기
    public List<GetMyPageArticleRes> getEditorArticles(Integer userId, Integer editorId) {
        User user = USER_VALIDATION(userId);

        Editor editor = editorRepository.findById(editorId).orElseThrow();
        List<ArticleAndEditor> findAllArticle = articleAndEditorRepository.findAllByEditorOrderByModifiedAt(editor);
        List<GetMyPageArticleRes> getEditorArticleRes = new ArrayList<>();

        for(ArticleAndEditor editorArticle : findAllArticle){
            List<String> editors = editorToString(editorArticle.getArticle());
            GetMyPageArticleRes dto = GetMyPageArticleRes.builder()
                    .id(editorArticle.getArticle().getId())
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
}
