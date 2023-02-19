package com.mews.mews_backend.domain.editor.repository;

import com.mews.mews_backend.domain.editor.entity.Editor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface EditorRepository extends JpaRepository<Editor, Integer> {

    // name like query
    List<Editor> findAllByNameContains(String keyword);

    @Modifying
    @Query("update Editor e set e.name = :inputName, e.imgUrl = :inputImgUrl, e.introduction = :inputIntroduction where e.id = :inputId")
    Integer updateById(@Param("inputId") Integer inputId,
                       @Param("inputName") String inputName,
                       @Param("inputImgUrl") String inputImgUrl,
                       @Param("inputIntroduction") String inputIntroduction);
}
