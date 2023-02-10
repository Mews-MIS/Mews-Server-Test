package com.mews.mews_backend.api.show.controller;

import com.mews.mews_backend.api.show.dto.request.PostShowReq;
import com.mews.mews_backend.api.show.dto.response.GetShowRes;
import com.mews.mews_backend.domain.show.service.ShowService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Main Page Show API"})
@RequestMapping("/show")
public class ShowController {

    private final ShowService showService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody PostShowReq postShowReq) {
        showService.register(postShowReq);

        return ResponseEntity.ok("Post Success");
    }

    @DeleteMapping("/delete/{show_id}")
    public ResponseEntity<String> delete(@PathVariable ("show_id") Integer id) {
        showService.delete(id);

        return ResponseEntity.ok("Delete Success");
    }

    @GetMapping("/getall")
    public ResponseEntity<List<GetShowRes>> getAll() {
        List<GetShowRes> getShowResList = showService.getAll();

        return ResponseEntity.ok(getShowResList);
    }
}
