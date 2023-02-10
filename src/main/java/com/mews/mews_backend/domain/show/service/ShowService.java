package com.mews.mews_backend.domain.show.service;

import com.mews.mews_backend.api.show.dto.request.PostShowReq;
import com.mews.mews_backend.api.show.dto.response.GetShowRes;
import com.mews.mews_backend.domain.show.entity.Show;
import com.mews.mews_backend.domain.show.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShowService {

    private final ShowRepository showRepository;

    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public void register(PostShowReq postShowReq) {
        Show show = postShowReq.toEntity(postShowReq);

        showRepository.save(show);
    }

    public void delete(Integer id) {
        showRepository.deleteById(id);
    }

    public List<GetShowRes> getAll() {
        return showRepository.findAll()
                .stream()
                .map(GetShowRes::new)
                .collect(Collectors.toList());
    }
}
