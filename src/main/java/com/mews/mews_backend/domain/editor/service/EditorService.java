package com.mews.mews_backend.domain.editor.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mews.mews_backend.api.editor.dto.request.PatchEditorReq;
import com.mews.mews_backend.api.editor.dto.request.PostEditorReq;
import com.mews.mews_backend.api.editor.dto.response.GetEditorRes;
import com.mews.mews_backend.domain.editor.entity.Editor;
import com.mews.mews_backend.domain.editor.repository.EditorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EditorService {

    private final EditorRepository editorRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // Editor DB 등록
    public void save(PostEditorReq postEditorReq, MultipartFile multipartFile) {
        String img = null;
        if(multipartFile != null & !multipartFile.isEmpty()) {
            try {
                img = updateImage(multipartFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Editor editor = postEditorReq.toEntity(postEditorReq, img);

        editorRepository.save(editor);
    }

    // Editor DB 수정
    public void update(PatchEditorReq patchEditorReq, MultipartFile multipartFile) {
        Editor editor = editorRepository.findById(patchEditorReq.getId()).get();

        String inputName = (patchEditorReq.getName() == null? editor.getName() : patchEditorReq.getName());
        String inputIntroduction = (patchEditorReq.getIntroduction() == null? editor.getIntroduction() : patchEditorReq.getIntroduction());

        String inputImgUrl = editor.getImgUrl();
        if(multipartFile != null & !multipartFile.isEmpty()) {
            try {
                inputImgUrl = updateImage(multipartFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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

    //이미지 넣기
    public String updateImage(MultipartFile multipartFile) throws IOException {
        LocalDate now = LocalDate.now();
        String uuid = UUID.randomUUID()+toString();
        String fileName = uuid+"_"+multipartFile.getOriginalFilename();
        String userImg = "user/" + now+"/"+ fileName;
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3Client.putObject(bucket, userImg, multipartFile.getInputStream(), objMeta);

        String img = amazonS3Client.getUrl(bucket, fileName).toString();

        return img;
    }
}
