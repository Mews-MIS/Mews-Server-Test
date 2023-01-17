package com.mews.mews_backend.domain.calendar.api;

import com.mews.mews_backend.domain.calendar.domain.calendarDomain;
import com.mews.mews_backend.domain.calendar.application.calendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class calendarController {

    private final calendarService calendarService;

    @PostMapping("/write")
    public calendarDomain writeCalendar() {
        calendarDomain calendarDomain = new calendarDomain();

        calendarDomain.setRegDate(LocalDateTime.now());

        return calendarDomain;
    }
}
