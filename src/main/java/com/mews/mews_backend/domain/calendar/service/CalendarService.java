package com.mews.mews_backend.domain.calendar.service;

import com.mews.mews_backend.api.calendar.dto.request.PatchCalendarReq;
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
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
    public void update(PatchCalendarReq patchCalendarReq) {
        Calendar calendar = calendarRepository.findById(patchCalendarReq.getId()).get();

        String inputTitle = (patchCalendarReq.getTitle() == null? calendar.getTitle() : patchCalendarReq.getTitle());
        String inputCategory = (patchCalendarReq.getCategory() == null? calendar.getCategory() : patchCalendarReq.getCategory());
        LocalDate inputStartDate = (patchCalendarReq.getStartDate() == null? calendar.getStartDate() : patchCalendarReq.getStartDate());
        LocalDate inputEndDate = (patchCalendarReq.getEndDate() == null? calendar.getEndDate() : patchCalendarReq.getEndDate());;

        calendarRepository.updateById(patchCalendarReq.getId(), inputTitle, inputCategory, inputStartDate, inputEndDate);
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
    public List<GetCalendarDayRes> getCalendarDay(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(string, formatter);

        return calendarRepository.findAllByStartDate(date).stream()
                .map(GetCalendarDayRes::new)
                .collect(Collectors.toList());
    }

    // 특정 년월의 학사 일정 DB SELECT
    public List<GetCalendarMonthRes> getCalendarMonth(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth date = YearMonth.parse(string, formatter);

        LocalDate firstDate = date.atDay(1);
        LocalDate lastDate = date.atEndOfMonth();

        return calendarRepository.findAllByStartDateBetween(firstDate, lastDate).stream()
                .map(GetCalendarMonthRes::new)
                .collect(Collectors.toList());
    }

    public List<GetCalendarOneRes> getCalendarAll() {
        return calendarRepository.findAll().stream()
                .map(GetCalendarOneRes::new)
                .collect(Collectors.toList());
    }
}
