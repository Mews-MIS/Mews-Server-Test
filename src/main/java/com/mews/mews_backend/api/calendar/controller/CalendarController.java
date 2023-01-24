package com.mews.mews_backend.api.calendar.controller;

import com.mews.mews_backend.api.calendar.dto.request.PostCalendarReq;
import com.mews.mews_backend.api.calendar.dto.response.GetCalendarMonthRes;
import com.mews.mews_backend.domain.calendar.entity.Calendar;
import com.mews.mews_backend.domain.calendar.service.CalendarService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Mews Calendar API"})
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    // 월별 페이지 조회 API
    // ing
    @GetMapping("/{yyyy_mm}")
    public ResponseEntity<List<GetCalendarMonthRes>> getMonthCalendar(@PathVariable("yyyy_mm") String yearMonth) {
        List<GetCalendarMonthRes> getCalendarMonthRes = new ArrayList<>();

        //calendar = calendarService.getCalendarMonthList('2022', '09');

        return ResponseEntity.ok(getCalendarMonthRes);
    }

    // 세부 일정 조회 API
    // ing
    @GetMapping("/{yyyy_mm_dd}")
    public ResponseEntity<String> getDayCalendar(@PathVariable("yyyy_mm_dd") String yearMonthDay) {
        Calendar calendar = new Calendar();

        return ResponseEntity.ok("Success");
    }

    // 세부 일정 등록 API
    @PostMapping("/write")
    public ResponseEntity<String> writeDayCalendar(@PathVariable("yyyy_mm_dd") String yearMonthDay, @Valid @RequestBody PostCalendarReq postCalendarReq) {
        calendarService.write(postCalendarReq);

        return ResponseEntity.ok("Success");
    }

    // 세부 일정 수정
    // ing
    @PutMapping("/{yyyy_mm_dd}/update/{calendar_id)")
    public ResponseEntity<Calendar> updateDayCalendar(@PathVariable("yyyy_mm_dd") String yearMonthDay, @PathVariable("calendar_id") Integer id, @RequestBody Calendar calendar) {

        return ResponseEntity.ok(calendar);
    }

    // 세부 일정 삭제 API
    // ing
    @DeleteMapping("/delete/{calendar_id)")
    public ResponseEntity<String> deleteDayCalendar(@PathVariable("calendar_id") Integer id) {
        calendarService.delete(id);

        return ResponseEntity.ok("Delete Success");
    }


}
