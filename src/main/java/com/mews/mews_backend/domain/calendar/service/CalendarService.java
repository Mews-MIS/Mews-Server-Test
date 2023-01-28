package com.mews.mews_backend.domain.calendar.service;

import com.mews.mews_backend.api.calendar.dto.request.PostCalendarReq;
import com.mews.mews_backend.api.calendar.dto.response.GetCalendarDayRes;
import com.mews.mews_backend.api.calendar.dto.response.GetCalendarMonthRes;
import com.mews.mews_backend.api.calendar.dto.response.GetCalendarOneRes;
import com.mews.mews_backend.domain.calendar.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mews.mews_backend.domain.calendar.entity.Calendar;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class CalendarService {

    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    // 학사 일정 DB INSERT
    public void save(PostCalendarReq postCalendarReq) {
        Calendar calendar = postCalendarReq.toEntity(postCalendarReq);

        calendarRepository.save(calendar);
    }

    // 학사 일정 DB UPDATE
    public void update(Integer id, PostCalendarReq postCalendarReq) {
        Calendar calendar = postCalendarReq.toEntity(postCalendarReq);

        // UPDATE는 SAVE 사용 X
        //calendarRepository.save(calendar);
    }

    // 학사 일정 DB DELETE
    public void delete(Integer id) {
        calendarRepository.deleteById(id);
    }

    // 특정 학사 일정 DB SELECT
    public GetCalendarOneRes getCalendarOne(Integer id) {
        // Optional 관련 발생하여 별도 처리 진행
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no such data"));

        GetCalendarOneRes getCalendarOneRes = new GetCalendarOneRes(calendar);
        return getCalendarOneRes;
    }

    // 특정 날짜의 학사 일정 DB SELECT
    public List<GetCalendarDayRes> getCalendarDay(LocalDate date) {
        return calendarRepository.findAllByDate(date).stream()
                .map(GetCalendarDayRes::new)
                .collect(Collectors.toList());
    }

    // 특정 년월의 학사 일정 DB SELECT
    public List<GetCalendarMonthRes> getCalendarMonth(LocalDate date) {
        return calendarRepository.findAllByDate(date).stream()
                .map(GetCalendarMonthRes::new)
                .collect(Collectors.toList());
    }
}
