package com.mews.mews_backend.domain.editor.service;

import com.mews.mews_backend.api.Editor.dto.request.PatchEditorReq;
import com.mews.mews_backend.api.Editor.dto.request.PostEditorReq;
import com.mews.mews_backend.api.Editor.dto.response.GetEditorRes;
import com.mews.mews_backend.domain.calendar.entity.Calendar;
import com.mews.mews_backend.domain.editor.entity.Editor;
import com.mews.mews_backend.domain.editor.repository.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EditorService {

    private final EditorRepository editorRepository;

    @Autowired
    public EditorService(EditorRepository editorRepository) {
        this.editorRepository = editorRepository;
    }

    // Editor DB 등록
    public void save(PostEditorReq postEditorReq) {
        Editor editor = postEditorReq.toEntity(postEditorReq);

        editorRepository.save(editor);
    }

    // Editor DB 수정
    public void update(PatchEditorReq patchEditorReq) {
        Editor editor = editorRepository.findById(patchEditorReq.getId()).get();

        String inputName = (patchEditorReq.getName() == null? editor.getName() : patchEditorReq.getName());
        String inputImgUrl = (patchEditorReq.getImgUrl() == null? editor.getImgUrl() : patchEditorReq.getImgUrl());
        String inputIntroduction = (patchEditorReq.getIntroduction() == null? editor.getIntroduction() : patchEditorReq.getIntroduction());

        editorRepository.updateById(patchEditorReq.getId(), inputName, inputImgUrl, inputIntroduction);
    }

    // Editor DB 삭제
    public void delete(Integer id) {
        editorRepository.deleteById(id);
    }

    // Editor DB 전체 조회
    public List<GetEditorRes> getAll() {
        List<GetEditorRes> getEditorRes = editorRepository.findAll().stream()
                .map(GetEditorRes::new)
                .collect(Collectors.toList());

        return getEditorRes;
    }

    // 특정 필진 DB 조회
    public GetEditorRes getOne(Integer id) {
        Editor editor = editorRepository.findById(id).get();

        GetEditorRes getEditorRes = new GetEditorRes(editor);

        return getEditorRes;
    }

}
