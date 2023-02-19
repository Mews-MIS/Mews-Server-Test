package com.mews.mews_backend.domain.curation.repository;

import com.mews.mews_backend.domain.curation.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CurationRepository extends JpaRepository<Curation, Integer> {

    Curation findByIdAndOpen(Integer id, Boolean open);

    @Modifying
    @Query("update Curation c set c.open = false where c.id = :inputId and c.open = true")
    void updateOpenById(@Param("inputId") Integer id);
}
