package com.mews.mews_backend.domain.comment.entity;

import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.common.BaseTimeEntity;
import com.mews.mews_backend.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comment_id; // 댓글 id

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article; // 게시물

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 댓글 작성 유저

    private String content; // 댓글 내용
}
