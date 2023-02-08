package com.mews.mews_backend.api.search.controller;

import com.mews.mews_backend.api.search.dto.response.PostSearchRes;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Search API"})
@RequestMapping("/search")
public class SearchController {

    @PostMapping(value = "/{keyword}")
    public ResponseEntity<PostSearchRes> Search(@PathVariable ("keyword") String string) {
        PostSearchRes postSearchRes = new PostSearchRes();


        return ResponseEntity.ok(postSearchRes);
    }
}
