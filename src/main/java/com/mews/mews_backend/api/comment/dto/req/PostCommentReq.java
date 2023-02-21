package com.mews.mews_backend.api.comment.dto.req;

import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.comment.entity.Comment;
import com.mews.mews_backend.domain.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostCommentReq {
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

    public Comment buildComment(PostCommentReq postCommentReq, Article article, User user){
        Comment comment = Comment.builder()
                .user(user)
                .article(article)
                .content(postCommentReq.getContent())
                .build();
        return comment;
    }
}
