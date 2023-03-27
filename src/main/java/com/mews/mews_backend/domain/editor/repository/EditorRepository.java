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

    List<Editor> findAllByDeleted(Boolean bool);

    Editor findByIdAndDeleted(Integer id, Boolean bool);

    // name like query
    List<Editor> findAllByNameContains(String keyword);

    @Modifying
    @Query("update Editor e set e.name = :inputName, e.imgUrl = :inputImgUrl, e.introduction = :inputIntroduction where e.id = :inputId")
    Integer updateById(@Param("inputId") Integer inputId,
                       @Param("inputName") String inputName,
                       @Param("inputImgUrl") String inputImgUrl,
                       @Param("inputIntroduction") String inputIntroduction);

    @Query(value = "select e.editor_id, e.created_at, e.modified_at, e.img_url, e.introduction, e.name, e.deleted " +
            "from editor e " +
            "where e.editor_id in (select s.editor_id from subscribe s where s.user_id = :id) "
            , nativeQuery = true)
    List<Editor> findAllByUserId(@Param("id")Integer id);

    @Query(value = "select e.editor_id, e.created_at, e.modified_at, e.img_url, e.introduction, e.name " +
            "from editor e " +
            "where e.editor_id in (select aae.editor_id from article_and_editor aae where aae.article_id = :id)"
            , nativeQuery = true)
    List<Editor> findAllByArticleId(@Param("id") Integer integer);

    @Modifying
    @Query(value = "update Editor e set e.deleted = true where e.id = :id")
    void updateDeleteById(@Param("id") Integer id);
}
