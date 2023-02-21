package com.mews.mews_backend.domain.curation.entity;

import com.mews.mews_backend.domain.common.BaseTimeEntity;
import com.mews.mews_backend.infra.StringListConverter;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "curation")
public class Curation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curation_id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content_list")
    @Convert(converter = StringListConverter.class)
    private List<String> list;

    @Column(name = "open")
    private Boolean open;

    // DTO to Entity
    @Builder
    public Curation(String title, List<String> list, Boolean open) {
        this.title = title;
        this.list = list;
        this.open = open;
    }

    public Curation update(String title, List<String> list) {
        this.title = title;
        this.list = list;

        return this;
    }
}
