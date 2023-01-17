package com.mews.mews_backend.domain.calendar.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class calendarDomain {

    @Id
    @GeneratedValue
    @Column(name = "calender_id")
    private Integer id;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "title")
    private String title;

    @Column(name = "inform")
    private String inform;
}
