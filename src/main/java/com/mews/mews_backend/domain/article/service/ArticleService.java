package com.mews.mews_backend.domain.article.service;

import com.mews.mews_backend.api.article.dto.req.PatchArticleReq;
import com.mews.mews_backend.api.article.dto.req.PostArticleReq;
import com.mews.mews_backend.api.article.dto.res.GetArticleRes;
import com.mews.mews_backend.api.article.dto.res.GetCheckArticle;
import com.mews.mews_backend.api.article.dto.res.GetMainArticleRes;
import com.mews.mews_backend.api.article.dto.res.GetPageArticleRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.entity.ArticleAndEditor;
import com.mews.mews_backend.domain.article.entity.Views;
import com.mews.mews_backend.domain.article.repository.ArticleAndEditorRepository;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.article.repository.ViewsRepository;
import com.mews.mews_backend.domain.editor.entity.Editor;
import com.mews.mews_backend.domain.editor.repository.EditorRepository;
import com.mews.mews_backend.domain.user.entity.Bookmark;
import com.mews.mews_backend.domain.user.entity.Like;
import com.mews.mews_backend.domain.user.entity.User;
import com.mews.mews_backend.domain.user.repository.BookmarkRepository;
import com.mews.mews_backend.domain.user.repository.LikeRepository;
import com.mews.mews_backend.domain.user.repository.SubscribeRepository;
import com.mews.mews_backend.domain.user.repository.UserRepository;
import com.mews.mews_backend.global.error.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.mews.mews_backend.global.error.ErrorCode.NOT_AUTHENTICATED_USER;
import static java.util.Arrays.stream;

@Service
@Transactional
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ViewsRepository viewsRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ArticleAndEditorRepository articleAndEditorRepository;
    private final EditorRepository editorRepository;

    private final SubscribeRepository subscribeRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ViewsRepository viewsRepository,
                          BookmarkRepository bookmarkRepository,
                          UserRepository userRepository,
                          LikeRepository likeRepository,
                          ArticleAndEditorRepository articleAndEditorRepository,
                          EditorRepository editorRepository,
                          SubscribeRepository subscribeRepository) {
        this.articleRepository = articleRepository;
        this.viewsRepository = viewsRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.articleAndEditorRepository = articleAndEditorRepository;
        this.editorRepository = editorRepository;
        this.subscribeRepository = subscribeRepository;
    }

    // 뉴스, 조회수 db 등록
    public void postArticle(PostArticleReq postArticleReq){
        Article article = buildArticle(postArticleReq);
        Views views = buildViews();
        article.setViews(views);
        articleRepository.save(article);
        viewsRepository.save(views);
        //필진 추가
        List<Integer> editors = postArticleReq.getEditors();
        for(int i=0;i<editors.size();i++){
            Editor findEditor = editorRepository.findById(editors.get(i)).orElseThrow();
            ArticleAndEditor articleAndEditor = ArticleAndEditor.builder()
                    .editor(findEditor)
                    .article(article)
                    .build();
            articleAndEditorRepository.save(articleAndEditor);
        }
    }

    // 뉴스 조회(페이지네이션)
    public List<GetPageArticleRes> getAllArticle(Integer page){
        PageRequest pageRequest = PageRequest.of(page, 10); // size 10으로 고정
        Page<Article> articleResPage = articleRepository.findAllByIsDeletedFalseOrderByModifiedAtDesc(pageRequest);
        List<Article> articles = articleResPage.getContent();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isBookmarked = false;
        boolean isLiked = false;

        List<GetPageArticleRes> getPageArticleRes = new ArrayList<>();
        for(Article article : articles) {

            List<ArticleAndEditor> articleAndEditors = articleAndEditorRepository.findAllByArticle(article);
            List<Editor> editors = new ArrayList<>();
            for(ArticleAndEditor articleAndEditor : articleAndEditors) {
                editors.add(articleAndEditor.getEditor());
            }

            if(!(authentication.getName().equals("anonymousUser"))){
                User user = userRepository.findByUserEmail(authentication.getName()).orElseThrow();
                isBookmarked = bookmarkRepository.existsByUserAndArticle(user, article);
                isLiked = likeRepository.existsByArticleAndUser(article, user);
            }

            getPageArticleRes.add(new GetPageArticleRes(article, editors,isLiked,isBookmarked));
        }

        return getPageArticleRes;
    }

    // 전체 페이지 개수 구하기
    public Integer getPageCount(){
        Integer newsCount = articleRepository.countByIsDeletedFalse();
        return (newsCount / 10) + 1;
    }

    // 뉴스 조회
    public GetArticleRes viewArticle(Integer articleId){
        Article article = articleRepository.findById(articleId).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isBookmarked = false;
        boolean isLiked = false;

        if(!(authentication.getName().equals("anonymousUser"))){
            User user = userRepository.findByUserEmail(authentication.getName()).orElseThrow();
            isBookmarked = bookmarkRepository.existsByUserAndArticle(user, article);
            isLiked = likeRepository.existsByArticleAndUser(article, user);
        }

        // 필진 id 탐색
        List<ArticleAndEditor> articleAndEditorList = articleAndEditorRepository.findAllByArticle_Id(articleId);
        List<Integer> editorIdList = new ArrayList<>();
        for(ArticleAndEditor elem : articleAndEditorList)
            editorIdList.add(elem.getEditor().getId());

        return GetArticleRes.from(article, isBookmarked, isLiked, editorIdList);
    }

    // 뉴스 수정
    public void updateArticle(PatchArticleReq patchArticleReq){
        Article originArticle = articleRepository.findById(patchArticleReq.getId()).get();

        String title = (patchArticleReq.getTitle() == null? originArticle.getTitle() : patchArticleReq.getTitle());
        String content = (patchArticleReq.getContent() == null? originArticle.getContent() : patchArticleReq.getContent());
        String type = (patchArticleReq.getType() == null? originArticle.getType() : patchArticleReq.getType());
        List<String> fileUrls = (patchArticleReq.getFileUrls() == null? originArticle.getFileUrls() : patchArticleReq.getFileUrls());

        Article newArticle = originArticle.update(title, content, type, fileUrls);
        articleRepository.save(newArticle);
    }

    // 뉴스 삭제 (논리적)
    public void deleteArticle(Integer articleId){
        Article article = articleRepository.findById(articleId).get();
        article.deleteArticle();
        articleRepository.save(article);

        // 좋아요 수, 북마크 수 cnt 감소
        userRepository.updateLikeCntById(articleId);
        userRepository.updateBookmarkCntById(articleId);
    }

//    // 뉴스 삭제(물리적)
//    public void deleteArticle(Integer articleId){
//        Article article = articleRepository.findById(articleId).get();
//        articleRepository.delete(article);
//    }

    // 해당 타입 게시글 전체 조회
    public List<Article> getAllTypeArticle(String type){
        List<Article> articleList = articleRepository.findAllByType(type);
        return articleList;
    }

    public List<Article> getFiveTopViewsArticle(){
        List<Article> articleList = articleRepository.findTop5ByOrderByViewsViewsDesc();
        return articleList;
    }

    public List<GetMainArticleRes> getSubscribeArticle(Integer id) {
        List<GetMainArticleRes> getMainArticleRes = new ArrayList<>();
        List<ArticleAndEditor> articleAndEditorList = new ArrayList<>();
        HashSet<Integer> id_set = new HashSet<>();
        // 구독한 필진 정보 찾기
        List<Editor> editorList = editorRepository.findAllByUserId(id);

        // 해당 필진이 작성한 기사 찾기 (삭제 여부 확인)
        for(Editor editor : editorList) {
            for(ArticleAndEditor articleAndEditor : articleAndEditorRepository.findAllByEditorOrderByModifiedAt(editor)) {
                if(articleAndEditor.getArticle().getIsDeleted() == false) {
                    articleAndEditorList.add(articleAndEditor);
                    id_set.add(articleAndEditor.getArticle().getId());
                }
            }
        }

        for(Integer article_id : id_set) {
            // user의 좋아요, 북마크 여부 확인
            Boolean userBookmark = bookmarkRepository.existsByUserIdAndArticleId(id, article_id);
            Boolean userLike = likeRepository.existsByUserIdAndArticleId(id, article_id);

            // 기사를 작성한 모든 필진 찾기
            List<Editor> editors = editorRepository.findAllByArticleId(article_id);

            // 기사 정보 가져오기
            Article article = articleRepository.findById(article_id).orElseThrow(IllegalArgumentException::new);

            // dto 생성 및 반환
            GetMainArticleRes getMainArticleRes1 = new GetMainArticleRes(article, editors, userBookmark, userLike);
            getMainArticleRes.add(getMainArticleRes1);
        }

        return getMainArticleRes;
    }

    public GetCheckArticle checkArticleLike(Integer articleId, Integer userId) {
        Boolean bookmark = (bookmarkRepository.existsByUserIdAndArticleId(userId, articleId) == Boolean.TRUE ? Boolean.TRUE : Boolean.FALSE);
        Boolean like = (likeRepository.existsByUserIdAndArticleId(userId, articleId) == Boolean.TRUE ? Boolean.TRUE : Boolean.FALSE);

        return new GetCheckArticle(bookmark, like);
    }

    private Article buildArticle(PostArticleReq postArticleReq){
        Article article= Article.builder()
                .title(postArticleReq.getTitle())
                .content(postArticleReq.getContent())
                .type(postArticleReq.getType())
                .fileUrls(postArticleReq.getFileUrls())
                .build();
        return article;
    }

    private Views buildViews(){
        Views views = Views.builder()
                .build();
        return views;
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
    public String insertBookmark(Integer userId, Integer articleId) {
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

            return "ADD BOOKMARK";
        } else {
            //북마크 삭제
            bookmarkRepository.deleteByIdAndArticleId(userId, articleId);
            //북마크cnt --
            user.downBookmark();
            userRepository.save(user);

            return "DELETE BOOKMARK";
        }


    }

    //좋아요
    public String insertlikeArticle(Integer userId, Integer articleId){
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

            return "ADD LIKEARTICLE";
        } else {     //좋아요 이미 되어 있는 글 => 좋아요 취소
            //좋아요 삭제
            likeRepository.deleteByIdAndArticleId(userId, articleId);

            //user likecnt --
            user.downLike();
            userRepository.save(user);

            //article likecnt --
            article.downLike();
            articleRepository.save(article);
            return "DELETE LIKEARTICLE";
        }
    }

}
