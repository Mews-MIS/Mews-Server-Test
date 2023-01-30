package com.mews.mews_backend.domain.article.entity;

import com.mews.mews_backend.domain.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "VIEWS")
public class Views extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer views_id; // 조회수 id

    @NotNull
    @ColumnDefault("0")
    private Integer views; // 조회수

    @OneToOne(mappedBy = "views", orphanRemoval = true)
    private Article article;

    @Builder
    public Views(Integer views){
        this.views = views;
    }

    @PrePersist
    public void prePersist(){
        this.views = this.views == null? 0 : this.views;
    }

    public Views setArticle(Article article){
        this.article = article;
        return this;
    }
}
