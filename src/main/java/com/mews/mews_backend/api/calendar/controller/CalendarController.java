package com.mews.mews_backend.api.calendar.controller;

import com.mews.mews_backend.api.calendar.dto.request.PostCalendarReq;
import com.mews.mews_backend.api.calendar.dto.response.GetCalendarDayRes;
import com.mews.mews_backend.api.calendar.dto.response.GetCalendarMonthRes;
import com.mews.mews_backend.api.calendar.dto.response.GetCalendarOneRes;
import com.mews.mews_backend.domain.calendar.service.CalendarService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Calendar API"})
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    // 세부 일정 등록 API
    @PostMapping("/write")
    public ResponseEntity<String> writeDayCalendar(@Valid @RequestBody PostCalendarReq postCalendarReq) {
        calendarService.save(postCalendarReq);

        return ResponseEntity.ok("Post Success");
    }

    // 세부 일정 수정 API
    @PutMapping("/{calendar_id}/update")
    public ResponseEntity<String> updateDayCalendar(@PathVariable("calendar_id") Integer id, @RequestBody PostCalendarReq postCalendarReq) {
        calendarService.update(id, postCalendarReq);


        return ResponseEntity.ok("Put Success");
    }

    // 세부 일정 삭제 API
    @DeleteMapping("/{calendar_id}/delete")
    public ResponseEntity<String> deleteDayCalendar(@PathVariable("calendar_id") Integer id) {
        calendarService.delete(id);

        return ResponseEntity.ok("Delete Success");
    }

    // 세부 일정 조회 API
    @GetMapping("/getone/{calendar_id}")
    public ResponseEntity<GetCalendarOneRes> getCalendarOne(@PathVariable("calendar_id") Integer id) {
        GetCalendarOneRes getCalendarOneRes = calendarService.getCalendarOne(id);

        return ResponseEntity.ok(getCalendarOneRes);
    }

    // 해당 날짜의 일정 조회 API
    @GetMapping("/getday/{yyyy_MM_dd}")
    public ResponseEntity<List<GetCalendarDayRes>> getCalendarDay(@PathVariable("yyyy_MM_dd") String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(string, formatter);

        List<GetCalendarDayRes> getCalendarDayRes = calendarService.getCalendarDay(date);

        return ResponseEntity.ok(getCalendarDayRes);
    }

    // 해당 년월의 일정 조회 API
    @GetMapping("/getmonth/{yyyy_MM}")
    public ResponseEntity<List<GetCalendarMonthRes>> getCalendarMonth(@PathVariable("yyyy_MM") String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate date = LocalDate.parse(string, formatter);

        List<GetCalendarMonthRes> getCalendarMonthRes = calendarService.getCalendarMonth(date);

        return ResponseEntity.ok(getCalendarMonthRes);
    }
}
