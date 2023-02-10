package com.mews.mews_backend.domain.show.repository;

import com.mews.mews_backend.domain.show.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ShowRepository extends JpaRepository<Show, Integer> {
}
