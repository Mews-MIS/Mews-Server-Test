package com.mews.mews_backend.domain.curation.service;

import com.mews.mews_backend.api.curation.dto.request.PatchCurationReq;
import com.mews.mews_backend.api.curation.dto.request.PostCurationReq;
import com.mews.mews_backend.api.curation.dto.response.GetCurationRes;
import com.mews.mews_backend.domain.curation.entity.Curation;
import com.mews.mews_backend.domain.curation.repository.CurationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CurationService {

    private final CurationRepository curationRepository;

    @Autowired
    public CurationService(CurationRepository curationRepository) {
        this.curationRepository = curationRepository;
    }

    public void postCuration(PostCurationReq postCurationReq) {
        Curation curation = postCurationReq.toEntity(postCurationReq);
        curationRepository.save(curation);
    }

    public void updateCuration(PatchCurationReq patchCurationReq) {
        Curation curation = curationRepository.findById(patchCurationReq.getId()).get();

        String inputTitle = (patchCurationReq.getTitle() == null? curation.getTitle() : patchCurationReq.getTitle());
        List<String> inputLists = (patchCurationReq.getList() == null? curation.getList() : patchCurationReq.getList());
        String inputBody = (patchCurationReq.getBody() == null? curation.getBody() : patchCurationReq.getBody());
        String inputInterview = (patchCurationReq.getInterview() == null? curation.getInterview() : patchCurationReq.getInterview());
        List<String> inputFileUrls = (patchCurationReq.getFileUrls() == null? curation.getFileUrls() : patchCurationReq.getFileUrls());

        Curation newCuration = curation.update(inputTitle, inputLists, inputBody, inputInterview, inputFileUrls);
        curationRepository.save(newCuration);
    }

    public void deleteCuration(Integer id) {
        curationRepository.updateOpenById(id);
    }

    public List<Curation> getAllCuration() {
        return curationRepository.findAll();
    }

    public GetCurationRes getCuration(Integer id) {
        Curation curation = curationRepository.findByIdAndOpen(id, Boolean.TRUE);

        if(curation == null) {
            throw new IllegalArgumentException();
        }

//        if(curation.getOpen() == false) {
//            GetCurationRes getCurationRes new GetCurationRes(Curation()::new);
//        }
//        else {
//
//        }

        GetCurationRes getCurationRes = new GetCurationRes(curation);

        return getCurationRes;
    }
}
