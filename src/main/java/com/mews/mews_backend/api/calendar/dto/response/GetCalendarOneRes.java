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

    private LocalDate date;

    private String inform;

    // Entity to DTO
    public GetCalendarOneRes(Calendar calendar) {
        this.id = calendar.getId();
        this.title = calendar.getTitle();;
        this.inform = calendar.getInform();;
        this.date = calendar.getDate();
    }
}
