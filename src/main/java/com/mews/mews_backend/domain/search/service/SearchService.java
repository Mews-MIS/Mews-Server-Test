package com.mews.mews_backend.domain.search.service;

import com.mews.mews_backend.api.search.dto.response.PostArticleRes;
import com.mews.mews_backend.api.search.dto.response.PostEditorRes;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.editor.repository.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchService {

    private final ArticleRepository articleRepository;

    private final EditorRepository editorRepository;

    @Autowired
    public SearchService(ArticleRepository articleRepository, EditorRepository editorRepository) {
        this.articleRepository = articleRepository;
        this.editorRepository = editorRepository;
    }

    public List<PostArticleRes> findArticle(String string) {
        return articleRepository.findAllByTitle(string)
                .stream()
                .map(PostArticleRes::new)
                .collect(Collectors.toList());
    }

    public List<PostEditorRes> findEditor(String string) {
        return editorRepository.findAllByName(string)
                .stream()
                .map(PostEditorRes::new)
                .collect(Collectors.toList());
    }

}
