package com.mews.mews_backend.domain.article.entity;


import com.mews.mews_backend.domain.common.BaseTimeEntity;
import com.mews.mews_backend.domain.editor.entity.Editor;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) //아무런 값 없을 때 객체 생성 막음
@Entity
public class ArticleAndEditor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_editor_id")
    private Integer id; // 교차테이블 id

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="editor_id")
    private Editor editor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="article_id")
    private Article article;
}
