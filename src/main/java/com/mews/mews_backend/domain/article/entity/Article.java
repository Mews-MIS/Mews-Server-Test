package com.mews.mews_backend.domain.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mews.mews_backend.domain.comment.entity.Comment;
import com.mews.mews_backend.domain.common.BaseTimeEntity;
import com.mews.mews_backend.infra.StringListConverter;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "ARTICLE")
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 콘텐츠 id

    private String title; // 제목

    private String content; // 마크다운 내용

    private String type; // 콘텐츠 타입

    private Boolean isDeleted; // 삭제 여부

    // 첨부파일 url
    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> fileUrls;

    @ColumnDefault("0")
    private Integer like_count; // 좋아요 수

    @JsonManagedReference // 순환참조 방지
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "views_id")
    private Views views;

    @JsonIgnore
    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("modifiedAt desc")
    private List<Comment> comments;

    public void setViews(Views views){
        this.views = views;
        this.views.setArticle(this);
    }

    public Article update(String title, String content, String type, List<String> fileUrls){
        this.title = title;
        this.content = content;
        this.type = type;
        this.fileUrls = fileUrls;
        return this;
    }

    public Article updateComment(List<Comment> comments){
        this.comments = comments;
        return this;
    }

    @Builder
    public Article(String title, String content, String type, Integer like_count, List<String> fileUrls){
        this.title = title;
        this.content = content;
        this.like_count = like_count;
        this.type = type;
        this.fileUrls = fileUrls;
        this.isDeleted = false;
    }

    public void upLike(){this.like_count++;}
    public void downLike(){this.like_count--;}

    public void deleteArticle(){
        this.isDeleted = true;
    }

    public void restoreArticle(){
        this.isDeleted = false;
    }

    // like_count default 값 0으로 설정
    // insert 되기 전 실행
    @PrePersist
    public void prePersist(){
        this.like_count = this.like_count == null? 0 : this.like_count;
    }
}
