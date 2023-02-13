package com.mews.mews_backend.api.show.controller;

import com.mews.mews_backend.api.show.dto.request.PostBannerReq;
import com.mews.mews_backend.api.show.dto.response.GetBannerRes;
import com.mews.mews_backend.domain.banner.service.BannerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Main Page Show API"})
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody PostBannerReq postBannerReq) {
        bannerService.register(postBannerReq);

        return ResponseEntity.ok("Post Success");
    }

    @DeleteMapping("/delete/{show_id}")
    public ResponseEntity<String> delete(@PathVariable ("show_id") Integer id) {
        bannerService.delete(id);

        return ResponseEntity.ok("Delete Success");
    }

    @GetMapping("/getall")
    public ResponseEntity<List<GetBannerRes>> getAll() {
        List<GetBannerRes> getBannerResList = bannerService.getAll();

        return ResponseEntity.ok(getBannerResList);
    }
}
