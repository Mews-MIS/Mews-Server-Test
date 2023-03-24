package com.mews.mews_backend.domain.search.service;

import com.mews.mews_backend.api.search.dto.response.GetArticleRes;
import com.mews.mews_backend.api.search.dto.response.GetEditorRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.entity.ArticleAndEditor;
import com.mews.mews_backend.domain.article.repository.ArticleAndEditorRepository;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.editor.entity.Editor;
import com.mews.mews_backend.domain.editor.repository.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchService {

    private final ArticleRepository articleRepository;

    private final EditorRepository editorRepository;

    private final ArticleAndEditorRepository articleAndEditorRepository;

    @Autowired
    public SearchService(ArticleRepository articleRepository, EditorRepository editorRepository, ArticleAndEditorRepository articleAndEditorRepository) {
        this.articleRepository = articleRepository;
        this.editorRepository = editorRepository;
        this.articleAndEditorRepository = articleAndEditorRepository;
    }

    public List<GetArticleRes> findArticle(String keyword) {
        // 삼중 조인 쿼리 사용
        List<GetArticleRes> getArticleResList = articleRepository.findAllByKeyword(keyword)
                .stream()
                .map(GetArticleRes::new)
                .collect(Collectors.toList());

        return getArticleResList;
    }

    public List<GetEditorRes> findEditor(String keyword) {
        return editorRepository.findAllByNameContains(keyword)
                .stream()
                .map(GetEditorRes::new)
                .collect(Collectors.toList());
    }

}
