package com.mews.mews_backend.domain.calendar.service;

import com.mews.mews_backend.api.calendar.dto.request.PostCalendarReq;
import com.mews.mews_backend.domain.calendar.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mews.mews_backend.domain.calendar.entity.Calendar;
import javax.transaction.Transactional;



@Service
@Transactional
public class CalendarService {

    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    // 학사 일정 DB INSERT
    public void write(PostCalendarReq postCalendarReq) {
        Calendar calendar = buildCalendar(postCalendarReq);

        calendarRepository.save(calendar);
    }

    // 학사 일정 DB DELETE
    public void delete(Integer id) {
        calendarRepository.deleteById(id);
    }




    private Calendar buildCalendar(PostCalendarReq postCalendarReq) {
        Calendar calendar = Calendar.builder()
                .title(postCalendarReq.getTitle())
                .inform(postCalendarReq.getInform())
                .date(postCalendarReq.getDate())
                .build();

        return calendar;
    }

//    // 특정 달에 해당하는 일정들 불러오기
//    List<com.mews.mews_backend.domain.calendar.entity.Calendar> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
//
//    // 특정 일자에 해당하는 일정들 불러오기
//    List<com.mews.mews_backend.domain.calendar.entity.Calendar> findAllByDate(LocalDate date);
//
//    // 특정 일정 하나 불러오기
//    com.mews.mews_backend.domain.calendar.entity.Calendar findOneById(Integer id);

}
