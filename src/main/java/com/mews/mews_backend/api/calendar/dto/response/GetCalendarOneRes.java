package com.mews.mews_backend.api.calendar.dto.response;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@Data
public class GetCalendarOneRes {

    private Integer id;

    private String title;

    private String category;

    private LocalDate startDate;

    private LocalDate endDate;

    // Entity to DTO
    public GetCalendarOneRes(Calendar calendar) {
        this.id = calendar.getId();
        this.title = calendar.getTitle();
        this.category = calendar.getCategory();
        this.startDate = calendar.getStartDate();
        this.endDate = calendar.getStartDate();
    }
}
