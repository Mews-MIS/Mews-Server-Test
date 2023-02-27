package com.mews.mews_backend.api.editor.controller;


import com.mews.mews_backend.api.editor.dto.request.PatchEditorReq;
import com.mews.mews_backend.api.editor.dto.request.PostEditorReq;
import com.mews.mews_backend.api.editor.dto.response.GetEditorAndArticleRes;
import com.mews.mews_backend.api.editor.dto.response.GetEditorRes;
import com.mews.mews_backend.domain.editor.service.EditorService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Editor API"})
@RequestMapping("/editor")
@Slf4j
public class EditorController {

    private final EditorService editorService;

    // 필진 등록 API
    @PostMapping("/register")
    public ResponseEntity<String> registerEditor(@RequestPart(value = "data") PostEditorReq postEditorReq,
                                                 @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        log.info("필진 등록");
        editorService.save(postEditorReq, multipartFile);

        return ResponseEntity.ok("Post Success");
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateEditor(@RequestPart(value = "data") PatchEditorReq patchEditorReq,
                                               @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        log.info("필진 정보 수정");
        editorService.update(patchEditorReq, multipartFile);

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

    @GetMapping("/getArticle/{editor_id}")
    public ResponseEntity<GetEditorAndArticleRes> getArticle(@PathVariable("editor_id") Integer id) {
        GetEditorAndArticleRes getEditorAndArticleRes = editorService.getArticle(id);

        return ResponseEntity.ok(getEditorAndArticleRes);
    }
}
