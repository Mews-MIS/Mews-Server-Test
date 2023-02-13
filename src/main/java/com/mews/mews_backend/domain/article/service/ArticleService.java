package com.mews.mews_backend.domain.article.service;

import com.mews.mews_backend.api.article.dto.req.PatchArticleReq;
import com.mews.mews_backend.api.article.dto.req.PostArticleReq;
import com.mews.mews_backend.api.article.dto.res.GetArticleRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.entity.Views;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.article.repository.ViewsRepository;
import com.mews.mews_backend.domain.user.entity.User;
import com.mews.mews_backend.domain.user.repository.BookmarkRepository;
import com.mews.mews_backend.domain.user.repository.LikeRepository;
import com.mews.mews_backend.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ViewsRepository viewsRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    private final LikeRepository likeRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ViewsRepository viewsRepository,
                          BookmarkRepository bookmarkRepository,
                          UserRepository userRepository,
                          LikeRepository likeRepository) {
        this.articleRepository = articleRepository;
        this.viewsRepository = viewsRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    // 뉴스, 조회수 db 등록
    public void postArticle(PostArticleReq postArticleReq){
        Article article = buildArticle(postArticleReq);
        Views views = buildViews();
        article.setViews(views);
        articleRepository.save(article);
        viewsRepository.save(views);
    }

    // 뉴스 조회(페이지네이션)
    public List<Article> getAllArticle(Integer page){
        PageRequest pageRequest = PageRequest.of(page, 10); // size 10으로 고정
        Page<Article> articleResPage = articleRepository.findAllByOrderById(pageRequest);
        List<Article> articles = articleResPage.getContent();
        return articles;
    }

    // 뉴스 조회
    public GetArticleRes viewArticle(Integer articleId){
        Article article = articleRepository.findById(articleId).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).orElseThrow();
        boolean isBookmarked = bookmarkRepository.existsByUserAndArticle(user, article);
        boolean isLiked = likeRepository.existsByArticleAndUser(article, user);
        return GetArticleRes.from(article, isBookmarked, isLiked);
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

    // 뉴스 삭제
    public void deleteArticle(Integer articleId){
        Article article = articleRepository.findById(articleId).get();
        articleRepository.delete(article);
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
}
