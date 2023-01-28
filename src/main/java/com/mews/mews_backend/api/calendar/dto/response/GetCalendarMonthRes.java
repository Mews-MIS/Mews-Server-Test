package com.mews.mews_backend.api.calendar.dto.response;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
public class GetCalendarMonthRes {

    private Integer id;

    private LocalDate date;


    // Entity to DTO
    public GetCalendarMonthRes(Calendar calendar) {
        this.id = calendar.getId();
        this.date = calendar.getDate();
    }

}
