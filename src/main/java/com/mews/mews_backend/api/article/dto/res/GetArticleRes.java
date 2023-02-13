package com.mews.mews_backend.api.article.dto.res;

import com.mews.mews_backend.api.comment.dto.res.GetCommentRes;
import com.mews.mews_backend.domain.article.entity.Article;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetArticleRes {
    @ApiModelProperty(notes = "뉴스 제목", example = "속보 mews 조회수 500만 돌파")
    private String title; // 제목

    @ApiModelProperty(notes = "마크다운 내용", example = "<h2>기사 내용</h2>")
    private String content; // 마크다운 내용

    @ApiModelProperty(notes = "타입", example = "article")
    private String type; // 타입

    @ApiModelProperty(notes = "첨부파일 url", example = "[\"www.google.com\", \"www.naver.com'\"]")
    private List<String> fileUrls; // 첨부파일 urls

    @ApiModelProperty(notes = "최종 수정 시각", example = "[2023,1,29,14,48,50]")
    private LocalDateTime modifiedAt; // 작성 시각

    @ApiModelProperty(notes = "조회 수", example = "500")
    private Integer views; // 조회 수

    @ApiModelProperty(notes = "좋아요 수", example = "500")
    private Integer likeCount; // 좋아요 수

    @ApiModelProperty(notes = "댓글", example = "[]")
    private List<GetCommentRes> comments; // 뉴스 댓글

    public static GetArticleRes from(Article article){
        return GetArticleRes.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .type(article.getType())
                .fileUrls(article.getFileUrls())
                .modifiedAt(article.getModifiedAt())
                .likeCount(article.getLike_count())
                .views(article.getViews().getViews())
                .comments(article.getComments().stream().map(GetCommentRes::new).collect(Collectors.toList()))
                .build();
    }
}
