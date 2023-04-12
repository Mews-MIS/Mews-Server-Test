package com.mews.mews_backend.domain.editor.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mews.mews_backend.api.article.dto.res.ArticleForEditor;
import com.mews.mews_backend.api.editor.dto.request.PatchEditorReq;
import com.mews.mews_backend.api.editor.dto.request.PostEditorReq;
import com.mews.mews_backend.api.editor.dto.response.EditorForArticle;
import com.mews.mews_backend.api.editor.dto.response.GetEditorAndArticleRes;
import com.mews.mews_backend.api.editor.dto.response.GetEditorRes;
import com.mews.mews_backend.api.editor.dto.response.GetEditorSubRes;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.entity.ArticleAndEditor;
import com.mews.mews_backend.domain.article.repository.ArticleAndEditorRepository;
import com.mews.mews_backend.domain.editor.entity.Editor;
import com.mews.mews_backend.domain.editor.repository.EditorRepository;
import com.mews.mews_backend.domain.user.entity.User;
import com.mews.mews_backend.domain.user.repository.SubscribeRepository;
import com.mews.mews_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EditorService {

    private final EditorRepository editorRepository;
    private final AmazonS3Client amazonS3Client;

    private final ArticleAndEditorRepository articleAndEditorRepository;

    private final SubscribeRepository subscribeRepository;

    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // Editor DB 등록
    public void save(PostEditorReq postEditorReq, MultipartFile multipartFile) {
        String img = null;
        if(multipartFile != null && !multipartFile.isEmpty()) {
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
        if(multipartFile != null && !multipartFile.isEmpty()) {
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

        editorRepository.updateDeleteById(id);

        // 삭제된 Editor 구독 Cnt 감소
        userRepository.updateSubCntById(id);
    }

    // Editor DB 전체 조회
    public List<GetEditorRes> getAll() {
        List<GetEditorRes> getEditorRes = editorRepository.findAllByDeleted(Boolean.FALSE).stream()
                .map(GetEditorRes::new)
                .collect(Collectors.toList());

        return getEditorRes;
    }

    // 특정 필진 DB 조회
    public GetEditorSubRes getOne(Integer id) {
        Boolean checkSub = Boolean.FALSE;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Editor editor = editorRepository.findByIdAndDeleted(id, Boolean.FALSE);

        if(!(authentication.getName().equals("anonymousUser"))) {
            User user = userRepository.findByUserEmail(authentication.getName()).orElseThrow();
            checkSub = subscribeRepository.existsByEditorIdAndUserId(id, user.getId());
        }

        GetEditorSubRes getEditorSubRes = new GetEditorSubRes(editor, checkSub);

        return getEditorSubRes;
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

    public GetEditorAndArticleRes getArticle(Integer id) {
        Editor editor = editorRepository.findById(id).orElseThrow();
        List<ArticleAndEditor> articleAndEditorList= articleAndEditorRepository.findAllByEditorOrderByModifiedAt(editor);
        List<ArticleForEditor> articleForEditors = new ArrayList<>();

        for(ArticleAndEditor articleAndEditor : articleAndEditorList) {
            List<EditorForArticle> editorForArticles = new ArrayList<>();
            List<ArticleAndEditor> articleAndEditorList1 = articleAndEditorRepository.findAllByArticle(articleAndEditor.getArticle());
            for(ArticleAndEditor articleAndEditor1 : articleAndEditorList1) {
                editorForArticles.add(new EditorForArticle(articleAndEditor1.getEditor()));
            }
            articleForEditors.add(new ArticleForEditor(articleAndEditor.getArticle(), editorForArticles));
        }

        GetEditorAndArticleRes getEditorAndArticleRes = new GetEditorAndArticleRes(editor, articleForEditors);

        return getEditorAndArticleRes;
    }

    public Boolean checkSub(Integer editorId, Integer userId) {
        Boolean check = (subscribeRepository.existsByEditorIdAndUserId(editorId, userId) ? Boolean.TRUE : Boolean.FALSE);

        return check;
    }
}
