package com.mews.mews_backend.domain.comment.service;

import com.mews.mews_backend.api.comment.dto.req.PostCommentReq;
import com.mews.mews_backend.domain.article.entity.Article;
import com.mews.mews_backend.domain.article.repository.ArticleRepository;
import com.mews.mews_backend.domain.comment.entity.Comment;
import com.mews.mews_backend.domain.comment.repository.CommentRepository;
import com.mews.mews_backend.domain.user.entity.User;
import com.mews.mews_backend.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public void postComment(PostCommentReq postCommentReq){
        Article article = articleRepository.findById(postCommentReq.getArticleId()).get();
        User user = userRepository.findById(postCommentReq.getUserId()).get();

        Comment comment = postCommentReq.buildComment(postCommentReq, article, user);
        commentRepository.save(comment);

        // article comments에 추가
        List<Comment> comments = article.getComments() == null? Collections.emptyList() : article.getComments();
        comments.add(comment);
        Article updateArticle = article.updateComment(comments);
        articleRepository.save(updateArticle);
    }


}
