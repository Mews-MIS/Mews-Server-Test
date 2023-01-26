package com.mews.mews_backend.api.calendar.dto.response;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class GetCalendarListRes {
    private Integer id;

    private String title;

    // Entity to DTO
    public GetCalendarListRes(Calendar calendar) {
        this.id = calendar.getId();
        this.title = calendar.getTitle();
    }
}
