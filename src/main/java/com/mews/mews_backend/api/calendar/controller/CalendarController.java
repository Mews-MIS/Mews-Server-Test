package com.mews.mews_backend.api.calendar.controller;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import com.mews.mews_backend.domain.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mews")
public class CalendarController {
    private final CalendarService calendarService;

    // 월별 페이지 조회 API
    @GetMapping("/calendar/{yyyy_mm}")
    public List<Calendar> getMonthCalendar(@PathVariable("yyyy_mm") String yearMonth) {
        List<Calendar> calendar = new ArrayList<>();

        return calendar;
    }

    // 세부 일정 조회 API
    @GetMapping("/calendar/{yyyy_mm_dd}")
    public Calendar getDayCalendar(@PathVariable("yyyy_mm_dd") String yearMonthDay) {
        Calendar calendar = new Calendar();

        return calendar;
    }

    // 세부 일정 등록 API
    @PostMapping("/calendar/{yyyy_mm_dd}/write")
    public Calendar writeDayCalendar(@PathVariable("yyyy_mm_dd") String yearMonthDay, @RequestBody Calendar calendar) {


        return calendar;
    }

    // 세부 일정 수정
    @PutMapping("/calendar/{yyyy_mm_dd}/update/{calendar_id)")
    public Calendar updateDayCalendar(@PathVariable("yyyy_mm_dd") String yearMonthDay, @PathVariable("calendar_id") Integer id, @RequestBody Calendar calendar) {

        return calendar;
    }

    // 세부 일정 삭제 API
    @PutMapping("/calendar/{yyyy_mm_dd}/delete/{calendar_id)")
    public Boolean deleteDayCalendar(@PathVariable("yyyy_mm_dd") String yearMonthDay, @PathVariable("calendar_id") Integer id) {

        return true;
    }


}
