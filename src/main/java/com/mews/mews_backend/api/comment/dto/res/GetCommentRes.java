package com.mews.mews_backend.api.comment.dto.res;

import com.mews.mews_backend.domain.comment.entity.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetCommentRes {
    @NotNull
    @ApiModelProperty(notes = "유저 아이디", example = "1")
    private Integer userId;

    @NotNull
    @ApiModelProperty(notes = "뉴스 게시물 아이디", example = "3")
    private Integer articleId;

    @NotBlank
    @Size(max = 300)
    @ApiModelProperty(notes = "댓글 내용(300자 제한)", example = "정말 멋있는 기사군요!")
    private String content;

    @NotNull
    @ApiModelProperty(notes = "댓글 최종 수정 시각", example = "[2023,1,29,14,48,50]")
    private LocalDateTime modifiedAt;

    public GetCommentRes(Comment comment) {
        this.userId = comment.getUser().getId();
        this.articleId = comment.getArticle().getId();
        this.content = comment.getContent();
        this.modifiedAt = comment.getModifiedAt();
    }
}
