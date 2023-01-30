package com.mews.mews_backend.api.calendar.controller;

import com.mews.mews_backend.api.calendar.dto.request.PatchCalendarReq;
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
import java.time.YearMonth;
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
    @PatchMapping("/update")
    public ResponseEntity<String> updateDayCalendar(@RequestBody PatchCalendarReq patchCalendarReq) {
        calendarService.update(patchCalendarReq);

        return ResponseEntity.ok("Patch Success");
    }

    // 세부 일정 삭제 API
    @DeleteMapping("/delete/{calendar_id}")
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
        List<GetCalendarDayRes> getCalendarDayRes = calendarService.getCalendarDay(string);

        return ResponseEntity.ok(getCalendarDayRes);
    }

    // 해당 년월의 일정 조회 API
    @GetMapping("/getmonth/{yyyy_MM}")
    public ResponseEntity<List<GetCalendarMonthRes>> getCalendarMonth(@PathVariable("yyyy_MM") String string) {
        List<GetCalendarMonthRes> getCalendarMonthRes = calendarService.getCalendarMonth(string);

        return ResponseEntity.ok(getCalendarMonthRes);
    }

    // 모든 일정 조회 API
    @GetMapping("/getall")
    public ResponseEntity<List<GetCalendarOneRes>> getCalendarAll() {
        List<GetCalendarOneRes> getCalendarOneRes = calendarService.getCalendarAll();

        return ResponseEntity.ok(getCalendarOneRes);
    }
}
