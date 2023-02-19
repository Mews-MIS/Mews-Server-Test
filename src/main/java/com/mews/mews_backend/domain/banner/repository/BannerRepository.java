package com.mews.mews_backend.domain.banner.repository;

import com.mews.mews_backend.domain.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BannerRepository extends JpaRepository<Banner, Integer> {
}
