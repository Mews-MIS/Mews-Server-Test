package com.mews.mews_backend.api.comment.controller;

import com.mews.mews_backend.api.comment.dto.req.PostCommentReq;
import com.mews.mews_backend.domain.comment.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Comment API"})
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @ApiOperation("댓글 작성")
    @PostMapping("/post")
    public ResponseEntity<String> postComment(@Valid @RequestBody PostCommentReq postCommentReq){
        commentService.postComment(postCommentReq);
        return ResponseEntity.ok("post comment success");
    }
}
