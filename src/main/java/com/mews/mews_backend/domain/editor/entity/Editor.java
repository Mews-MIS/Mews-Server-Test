package com.mews.mews_backend.domain.editor.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "Editor")
public class Editor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "editor_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "introduction")
    private String introduction;

    @Builder
    public Editor(String name, String imgUrl, String introduction) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.introduction = introduction;
    }
}
