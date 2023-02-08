package com.mews.mews_backend.domain.search.repository;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface SearchRepository extends JpaRepository<Calendar, Integer> {

}