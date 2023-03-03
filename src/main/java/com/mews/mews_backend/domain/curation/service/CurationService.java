package com.mews.mews_backend.domain.curation.service;

import com.mews.mews_backend.api.article.dto.res.GetCurationArticleRes;
import com.mews.mews_backend.api.curation.dto.request.PatchCurationReq;
import com.mews.mews_backend.api.curation.dto.request.PostCurationReq;
import com.mews.mews_backend.api.curation.dto.response.GetAllCurationRes;
import com.mews.mews_backend.api.curation.dto.response.GetCurationRes;
import com.mews.mews_backend.api.curation.dto.response.GetCurationTitleRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.entity.ArticleAndEditor;
import com.mews.mews_backend.domain.article.repository.ArticleAndEditorRepository;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.curation.entity.Curation;
import com.mews.mews_backend.domain.curation.repository.CurationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CurationService {

    private final CurationRepository curationRepository;

    private final ArticleRepository articleRepository;

    private final ArticleAndEditorRepository articleAndEditorRepository;

    @Autowired
    public CurationService(CurationRepository curationRepository, ArticleRepository articleRepository, ArticleAndEditorRepository articleAndEditorRepository) {
        this.curationRepository = curationRepository;
        this.articleRepository = articleRepository;
        this.articleAndEditorRepository = articleAndEditorRepository;
    }

    public void postCuration(PostCurationReq postCurationReq) {
        Curation curation = postCurationReq.toEntity(postCurationReq);
        curationRepository.save(curation);
    }

    public void updateCuration(PatchCurationReq patchCurationReq) {
        Curation curation = curationRepository.findById(patchCurationReq.getId()).get();

        String inputTitle = (patchCurationReq.getTitle() == null? curation.getTitle() : patchCurationReq.getTitle());
        List<String> inputLists = (patchCurationReq.getList() == null? curation.getList() : patchCurationReq.getList());

        Curation newCuration = curation.update(inputTitle, inputLists);
        curationRepository.save(newCuration);
    }

    public void deleteCuration(Integer id) {
        curationRepository.updateOpenById(id);
    }

    public GetAllCurationRes getAllCuration() {

        // 선택된 큐레이션
        List<Curation> findCurationCheckedTrue = curationRepository.findAllChecked();
        List<GetCurationTitleRes> curationChecked = new ArrayList<>();

        for(Curation curation : findCurationCheckedTrue){
            GetCurationTitleRes dto = GetCurationTitleRes.builder()
                    .id(curation.getId())
                    .title(curation.getTitle())
                    .build();
            curationChecked.add(dto);
        }

        // 모든 큐레이션
        List<Curation> findAllCuration = curationRepository.findAll();
        List<GetCurationTitleRes> curations = new ArrayList<>();

        for(Curation curation : findAllCuration){
            GetCurationTitleRes dto = GetCurationTitleRes.builder()
                    .id(curation.getId())
                    .title(curation.getTitle())
                    .build();

            curations.add(dto);
        }

        GetAllCurationRes allCurations = GetAllCurationRes.builder()
                .checked(curationChecked)
                .allCuration(curations)
                .build();

        return allCurations;
    }

    public GetCurationRes getCuration(Integer id) {
        Curation curation = curationRepository.findByIdAndOpen(id, Boolean.TRUE);

        // Curation 미존재시 Error
        if(curation == null) {
            throw new IllegalArgumentException();
        }

        List<String> articleList = curation.getList();
        List<GetCurationArticleRes> getCurationArticleRes = new ArrayList<>();
        for(String sId : articleList) {
            List<String> editors = new ArrayList<>();
            Integer articleId = Integer.parseInt(sId);

            Article article = articleRepository.findById(articleId).orElseThrow();
            List<ArticleAndEditor> articleAndEditorList = articleAndEditorRepository.findAllByArticle(article);
            for(ArticleAndEditor articleAndEditor : articleAndEditorList) {
                editors.add(articleAndEditor.getEditor().getName());
            }

            getCurationArticleRes.add(new GetCurationArticleRes(article, editors));
        }

        GetCurationRes getCurationRes = new GetCurationRes(curation, getCurationArticleRes);

        return getCurationRes;
    }

    public void patchCurationChecked(Integer id, Boolean checked){
        Curation curation = curationRepository.findById(id).orElseThrow();
        curation.updateChecked(checked);
        curationRepository.save(curation);
    }
}
