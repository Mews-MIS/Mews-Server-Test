package com.mews.mews_backend.api.calendar.dto.response;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
public class GetCalendarOneRes {

    @NotNull
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private LocalDate date;

    @NotBlank
    private String inform;

    // Entity to DTO
    public GetCalendarOneRes(Calendar calendar) {
        this.id = calendar.getId();
        this.title = calendar.getTitle();;
        this.inform = calendar.getInform();;
        this.date = calendar.getDate();
    }
}
