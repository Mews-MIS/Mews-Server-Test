package com.mews.mews_backend.api.Editor.controller;


import com.mews.mews_backend.api.Editor.dto.request.PatchEditorReq;
import com.mews.mews_backend.api.Editor.dto.request.PostEditorReq;
import com.mews.mews_backend.api.Editor.dto.response.GetEditorRes;
import com.mews.mews_backend.domain.editor.service.EditorService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PatchMapping("/update")
    public ResponseEntity<String> updateEditor(@Valid @RequestBody PatchEditorReq patchEditorReq) {
        editorService.update(patchEditorReq);

        return ResponseEntity.ok("Patch Success");
    }

    @DeleteMapping("/delete/{editor_id}")
    public ResponseEntity<String> deleteEditor(@PathVariable("editor_id") Integer id) {
        editorService.delete(id);

        return ResponseEntity.ok("Delete Success");
    }

    @GetMapping("/getall")
    public ResponseEntity<List<GetEditorRes>> getEditorAll() {
        List<GetEditorRes> getEditorAllRes = editorService.getAll();

        return ResponseEntity.ok(getEditorAllRes);
    }

    @GetMapping("/getone/{editor_id}")
    public ResponseEntity<GetEditorRes> getEditor(@PathVariable("editor_id") Integer id) {
        GetEditorRes getEditorRes = editorService.getOne(id);

        return ResponseEntity.ok(getEditorRes);
    }
}
