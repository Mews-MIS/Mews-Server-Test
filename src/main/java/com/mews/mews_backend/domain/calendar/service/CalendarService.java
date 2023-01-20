package com.mews.mews_backend.domain.calendar.service;

import com.mews.mews_backend.api.calendar.dto.response.GetCalendarMonthRes;
import com.mews.mews_backend.domain.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CalendarService {

    @Autowired
    private final CalendarRepository calendarRepository;

    public List<GetCalendarMonthRes> getCalendarMonthList(String year, String month) {
        List<GetCalendarMonthRes> getCalendarMonthResList = new ArrayList<>();

        return getCalendarMonthResList;
    }
}
