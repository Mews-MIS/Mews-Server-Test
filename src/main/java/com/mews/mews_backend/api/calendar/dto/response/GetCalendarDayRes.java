package com.mews.mews_backend.api.calendar.dto.response;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
public class GetCalendarDayRes {

    private String title;

    private String category;

    private LocalDate startDate;

    private LocalDate endDate;

    // Entity to DTO
    public GetCalendarDayRes(Calendar calendar) {
        this.title = calendar.getTitle();
        this.category = calendar.getCategory();
        this.startDate = calendar.getStartDate();
        this.endDate = calendar.getEndDate();
    }
}
