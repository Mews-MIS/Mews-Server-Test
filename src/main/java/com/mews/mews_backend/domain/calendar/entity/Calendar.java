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

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "inform")
    private String inform;

    @Builder
    public Calendar(LocalDate startDate, LocalDate endDate,String title, String type, String inform) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.type = type;
        this.inform = inform;
    }
}

