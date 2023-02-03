package com.mews.mews_backend.domain.editor.service;

import com.mews.mews_backend.api.Editor.dto.request.PatchEditorReq;
import com.mews.mews_backend.api.Editor.dto.request.PostEditorReq;
import com.mews.mews_backend.domain.editor.entity.Editor;
import com.mews.mews_backend.domain.editor.repository.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EditorService {

    private final EditorRepository editorRepository;

    @Autowired
    public EditorService(EditorRepository editorRepository) {
        this.editorRepository = editorRepository;
    }

    public void save(PostEditorReq postEditorReq) {
        Editor editor = postEditorReq.toEntity(postEditorReq);

        editorRepository.save(editor);
    }

    public void update(PatchEditorReq patchEditorReq) {
        Editor editor = editorRepository.findById(patchEditorReq.getId()).get();

        String inputName = (patchEditorReq.getName() == null? editor.getName() : patchEditorReq.getName());
        String inputImgUrl = (patchEditorReq.getImgUrl() == null? editor.getImgUrl() : patchEditorReq.getImgUrl());
        String inputIntroduction = (patchEditorReq.getIntroduction() == null? editor.getIntroduction() : patchEditorReq.getIntroduction());

        editorRepository.updateById(patchEditorReq.getId(), inputName, inputImgUrl, inputIntroduction);
    }

    public void delete(Integer id) {
        editorRepository.deleteById(id);
    }
}
