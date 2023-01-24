package com.mews.mews_backend.api.calendar.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCalendarMonthRes {
    private String date;

}
