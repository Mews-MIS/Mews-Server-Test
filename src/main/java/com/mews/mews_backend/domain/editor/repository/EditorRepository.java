package com.mews.mews_backend.domain.editor.repository;

import com.mews.mews_backend.domain.editor.entity.Editor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface EditorRepository extends JpaRepository<Editor, Integer> {

}
