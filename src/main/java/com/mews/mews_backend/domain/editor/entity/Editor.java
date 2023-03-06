package com.mews.mews_backend.domain.editor.entity;

import com.mews.mews_backend.domain.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "Editor")
public class Editor extends BaseTimeEntity {

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

    private Boolean deleted;

    @Builder
    public Editor(String name, String imgUrl, String introduction) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.introduction = introduction;
        this.deleted = Boolean.FALSE;
    }
}
