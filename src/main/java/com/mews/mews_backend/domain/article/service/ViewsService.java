package com.mews.mews_backend.domain.article.service;

import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.article.repository.ViewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ViewsService {
    private final ArticleRepository articleRepository;
    private final ViewsRepository viewsRepository;

    public ViewsService(ArticleRepository articleRepository, ViewsRepository viewsRepository) {
        this.articleRepository = articleRepository;
        this.viewsRepository = viewsRepository;
    }

    public int getViewId(Integer articleId){
        // todo : 에러 처리 테스트
        return articleRepository.findById(articleId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다")).getViews().getViews_id();
    }

    // 조회수 증가
    public void updateView(Integer id){
        viewsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        this.viewsRepository.updateView(id);
    }
}
