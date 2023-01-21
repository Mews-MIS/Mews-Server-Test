package com.mews.mews_backend.domain.article.entity;

import com.mews.mews_backend.domain.common.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "ARTICLE")
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer article_id; // 콘텐츠 id

    private String title; // 제목

    private String content; // 마크다운 내용

    private Integer like_count; // 좋아요 수

    private String type; // 콘텐츠 타입
}
