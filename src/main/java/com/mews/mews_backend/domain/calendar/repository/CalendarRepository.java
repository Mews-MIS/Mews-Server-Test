package com.mews.mews_backend.domain.calendar.repository;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
@EnableJpaRepositories
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    // 특정 날짜에 해당하는 일정 조회
    List<Calendar> findAllByStartDate(LocalDate date);

    // 특정 월에 해당하는 일정 조회
    List<Calendar> findAllByStartDateBetween(LocalDate firstDate, LocalDate lastDate);

    // 특정 일정 수정
    @Modifying
    @Query("update Calendar c set c.title = :inputTitle, c.category = :inputCategory, c.startDate = :startDate, c.endDate = :endDate where c.id = :inputId")
    Integer updateById(@Param("inputId") Integer inputId,
                       @Param("inputTitle") String inputTitle,
                       @Param("inputCategory") String inputCategory,
                       @Param("startDate") LocalDate inputStartDate,
                       @Param("endDate") LocalDate inputEndDate);
}
