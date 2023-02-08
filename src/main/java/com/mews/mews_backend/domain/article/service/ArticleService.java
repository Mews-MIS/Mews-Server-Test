package com.mews.mews_backend.domain.article.service;

import com.mews.mews_backend.api.article.dto.req.PatchArticleReq;
import com.mews.mews_backend.api.article.dto.req.PostArticleReq;
import com.mews.mews_backend.api.article.dto.res.GetArticleRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.entity.Views;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.article.repository.ViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ViewsRepository viewsRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ViewsRepository viewsRepository) {
        this.articleRepository = articleRepository;
        this.viewsRepository = viewsRepository;
    }

    // 뉴스, 조회수 db 등록
    public void postArticle(PostArticleReq postArticleReq){
        Article article = buildArticle(postArticleReq);
        Views views = buildViews();
        article.setViews(views);
        articleRepository.save(article);
        viewsRepository.save(views);
    }

    // 뉴스 조회
    public GetArticleRes viewArticle(Integer articleId){
        Article article = articleRepository.findById(articleId).get();
        return GetArticleRes.from(article);
    }

    // 뉴스 수정
    public void updateArticle(PatchArticleReq patchArticleReq){
        Article originArticle = articleRepository.findById(patchArticleReq.getId()).get();

        String title = (patchArticleReq.getTitle() == null? originArticle.getTitle() : patchArticleReq.getTitle());
        String content = (patchArticleReq.getContent() == null? originArticle.getContent() : patchArticleReq.getContent());
        String type = (patchArticleReq.getType() == null? originArticle.getType() : patchArticleReq.getType());

        Article newArticle = originArticle.update(title, content, type);
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
