package com.mews.mews_backend.api.curation.controller;


import com.mews.mews_backend.api.curation.dto.request.PatchCurationReq;
import com.mews.mews_backend.api.curation.dto.request.PostCurationReq;
import com.mews.mews_backend.api.curation.dto.response.GetCurationRes;
import com.mews.mews_backend.domain.curation.entity.Curation;
import com.mews.mews_backend.domain.curation.service.CurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Curation API"})
@RequestMapping("/curation")
public class CurationController {

    private final CurationService curationService;

    @ApiOperation("큐레이션 글 작성")
    @PostMapping("/post")
    public ResponseEntity<String> postCuration(@Valid @RequestBody PostCurationReq postCurationReq) {
        curationService.postCuration(postCurationReq);

        return ResponseEntity.ok("Post Success");
    }

    @ApiOperation("큐레이션 글 수정")
    @PatchMapping("/update")
    public ResponseEntity<String> updateCuration(@Valid @RequestBody PatchCurationReq patchCurationReq) {
        curationService.updateCuration(patchCurationReq);

        return ResponseEntity.ok("Update Success");
    }

    @ApiOperation("큐레이션 게시글 삭제")
    @PatchMapping("/delete/{id}")
    public ResponseEntity<String> deleteCuration(@PathVariable("id") Integer id) {
        curationService.deleteCuration(id);

        return ResponseEntity.ok("Delete Success");
    }

    @ApiOperation("큐레이션 전체글 가져오기")
    @GetMapping("/all")
    public ResponseEntity<List<Curation>> getAllCuration() {
        List<Curation> curations = curationService.getAllCuration();

        return ResponseEntity.ok(curations);
    }

    @ApiOperation("특정 큐레이션 가져오기")
    @GetMapping("/{id}")
    public ResponseEntity<GetCurationRes> getCuration(@PathVariable("id") Integer id) {
        GetCurationRes getCurationRes = curationService.getCuration(id);

        return ResponseEntity.ok(getCurationRes);
    }
}
