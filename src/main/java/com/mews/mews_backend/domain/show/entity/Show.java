package com.mews.mews_backend.domain.show.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Show")
public class Show {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "show_id")
    private Integer id;

    @Column(name = "article_id")
    private Integer article_id;

    @Builder
    public Show(Integer id, Integer article_id) {
        this.id = id;
        this.article_id = article_id;
    }
}
