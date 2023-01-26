package com.mews.mews_backend.api.calendar.controller;

import com.mews.mews_backend.api.calendar.dto.request.PostCalendarReq;
import com.mews.mews_backend.api.calendar.dto.response.GetCalendarListRes;
import com.mews.mews_backend.api.calendar.dto.response.GetCalendarOneRes;
import com.mews.mews_backend.domain.calendar.entity.Calendar;
import com.mews.mews_backend.domain.calendar.service.CalendarService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Calendar API"})
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    // 세부 일정 등록 API
    @PostMapping("/write")
    public ResponseEntity<String> writeDayCalendar(@PathVariable("yyyy_mm_dd") String yearMonthDay, @Valid @RequestBody PostCalendarReq postCalendarReq) {
        calendarService.save(postCalendarReq);

        return ResponseEntity.ok("Post Success");
    }

    // 세부 일정 수정 API
    @PutMapping("/{calendar_id)/update")
    public ResponseEntity<String> updateDayCalendar(@PathVariable("yyyy_mm_dd") String yearMonthDay, @PathVariable("calendar_id") Integer id, @RequestBody PostCalendarReq postCalendarReq) {
        calendarService.update(id, postCalendarReq);


        return ResponseEntity.ok("Put Success");
    }

    // 세부 일정 삭제 API
    @DeleteMapping("/{calendar_id)/delete")
    public ResponseEntity<String> deleteDayCalendar(@PathVariable("calendar_id") Integer id) {
        calendarService.delete(id);

        return ResponseEntity.ok("Delete Success");
    }

    // 세부 일정 조회 API
    @GetMapping("/{calendar_id}")
    public ResponseEntity<GetCalendarOneRes> getCalendarOne(@PathVariable("calendar_id") Integer id) {
        GetCalendarOneRes getCalendarOneRes = calendarService.getCalendarOne(id);

        return ResponseEntity.ok(getCalendarOneRes);
    }

    // 해당 날짜의 일정 조회 API
    @GetMapping("/{yyyy_MM_dd}")
    public ResponseEntity<List<GetCalendarListRes>> getCalendarDay(@PathVariable("yyyy_MM_dd") String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        LocalDate date = LocalDate.parse(string, formatter);

        List<GetCalendarListRes> getCalendarListRes = calendarService.getCalendarDay(date);

        return ResponseEntity.ok(getCalendarListRes);
    }
}
