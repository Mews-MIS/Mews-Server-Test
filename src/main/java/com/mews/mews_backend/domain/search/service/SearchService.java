package com.mews.mews_backend.domain.search.service;

import com.mews.mews_backend.domain.search.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SearchService {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }


}
