package com.mews.mews_backend.domain.banner.service;

import com.mews.mews_backend.api.show.dto.request.PostBannerReq;
import com.mews.mews_backend.api.show.dto.response.GetBannerRes;
import com.mews.mews_backend.domain.banner.entity.Banner;
import com.mews.mews_backend.domain.banner.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BannerService {

    private final BannerRepository bannerRepository;

    @Autowired
    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public void register(PostBannerReq postBannerReq) {
        Banner banner = postBannerReq.toEntity(postBannerReq);

        bannerRepository.save(banner);
    }

    public void delete(Integer id) {
        bannerRepository.deleteById(id);
    }

    public List<GetBannerRes> getAll() {
        return bannerRepository.findAll()
                .stream()
                .map(GetBannerRes::new)
                .collect(Collectors.toList());
    }
}
