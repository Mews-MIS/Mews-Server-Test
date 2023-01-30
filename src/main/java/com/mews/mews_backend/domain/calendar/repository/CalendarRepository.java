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
    List<Calendar> findAllByDate(LocalDate date);

    // 특정 일정 수정
    @Modifying
    @Query("update Calendar c set c.title = :inputTitle, c.inform = :inputInform, c.date = :inputDate")
    Integer updateById(@Param("inputTitle") String title,
                       @Param("inputInform") String inform,
                       @Param("inputDate") LocalDate date);
}
