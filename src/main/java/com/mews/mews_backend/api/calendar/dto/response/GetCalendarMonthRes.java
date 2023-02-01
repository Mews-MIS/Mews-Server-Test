package com.mews.mews_backend.api.calendar.dto.response;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
public class GetCalendarMonthRes {

    private LocalDate startDate;

    private LocalDate endDate;

    private String category;


    // Entity to DTO
    public GetCalendarMonthRes(Calendar calendar) {
        this.startDate = calendar.getStartDate();
        this.endDate = calendar.getEndDate();
        this.category = calendar.getCategory();
    }

}
