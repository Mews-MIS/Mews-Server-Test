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

    @Column(name = "body")
    private String body;

    @Column(name = "interview")
    private String interview;

    @Column(name = "file")
    @Convert(converter = StringListConverter.class)
    private List<String> fileUrls;

    @Column(name = "open")
    private Boolean open;

    // DTO to Entity
    @Builder
    public Curation(String title, List<String> list, String body, String interview, List<String> fileUrls, Boolean open) {
        this.title = title;
        this.list = list;
        this.body = body;
        this.interview = interview;
        this.fileUrls = fileUrls;
        this.open = open;
    }

    public Curation update(String title, List<String> list, String body, String interview, List<String> fileUrls) {
        this.title = title;
        this.list = list;
        this.body = body;
        this.interview = interview;
        this.fileUrls = fileUrls;

        return this;
    }
}
