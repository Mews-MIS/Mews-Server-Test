package com.mews.mews_backend.domain.user.repository;

import com.mews.mews_backend.domain.user.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository  extends JpaRepository<Subscribe, Integer> {

}
