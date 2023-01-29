package com.mews.mews_backend.domain.article.entity;

import com.mews.mews_backend.domain.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "ARTICLE")
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer article_id; // 콘텐츠 id

    private String title; // 제목

    private String content; // 마크다운 내용

    private String type; // 콘텐츠 타입

    @ColumnDefault("0")
    private Integer like_count; // 좋아요 수

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "views_id")
    private Views views;

    public void setViews(Views views){
        this.views = views;
        this.views.setArticle(this);
    }

    public Article update(String title, String content, String type){
        this.title = title;
        this.content = content;
        this.type = type;
        return this;
    }

    @Builder
    public Article(String title, String content, String type, Integer like_count){
        this.title = title;
        this.content = content;
        this.like_count = like_count;
        this.type = type;
    }

    // like_count default 값 0으로 설정
    // insert 되기 전 실행
    @PrePersist
    public void prePersist(){
        this.like_count = this.like_count == null? 0 : this.like_count;
    }
}
