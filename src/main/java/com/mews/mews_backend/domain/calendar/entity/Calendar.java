package com.mews.mews_backend.domain.calendar.entity;

import com.mews.mews_backend.domain.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "Calendar")
public class Calendar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_id")
    private Integer id;

    @Column(name = "calendar_date")
    private LocalDate date;

    @Column(name = "title")
    private String title;

    @Column(name = "inform")
    private String inform;

    @Builder
    public Calendar(String title, String inform, LocalDate date) {
        this.title = title;
        this.inform = inform;
        this.date = date;
    }
}

