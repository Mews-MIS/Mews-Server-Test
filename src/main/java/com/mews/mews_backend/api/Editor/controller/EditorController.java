package com.mews.mews_backend.api.Editor.controller;


import com.mews.mews_backend.api.Editor.dto.request.PostEditorReq;
import com.mews.mews_backend.domain.editor.service.EditorService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Editor API"})
@RequestMapping("/editor")
public class EditorController {

    private final EditorService editorService;

    // 필진 등록 API
    @PostMapping("/register")
    public ResponseEntity<String> registerEditor(@Valid @RequestBody PostEditorReq postEditorReq) {
        editorService.save(postEditorReq);

        return ResponseEntity.ok("Post Success");
    }
}
