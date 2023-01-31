package com.mews.mews_backend.api.file.controller;

import com.mews.mews_backend.api.file.dto.res.PostFileRes;
import com.mews.mews_backend.domain.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews File API"})
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    @ApiOperation("객체 업로드")
    @PostMapping("/upload")
    public ResponseEntity<PostFileRes> uploadFile(@RequestPart("file") List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(fileService.uploadFile(multipartFiles));
    }

//    @PostMapping("/deleteFile")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteImage(@Authenticated AuthInfo authInfo,@Valid @RequestBody DeleteImageRequestDto deleteImageRequestDto) {
//        imageService.deleteImage(authInfo.getEmail(), deleteImageRequestDto.getImageUrls());
//    }
}
