package com.mews.mews_backend.api.calendar.dto.request;

import com.mews.mews_backend.domain.calendar.entity.Calendar;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostCalendarReq {

    @NotBlank
    @ApiModelProperty(notes = "제목", example = "수강 신청 사전 선택")
    private String title;

    @NotBlank
    @ApiModelProperty(name = "일정 정보", example = "수강 신청 사전 선택 일자는 2023-02-06이다.")
    private String inform;

    @NotBlank
    @ApiModelProperty(notes = "날짜(년-월-일)", example = "2023-02-06")
    private LocalDate date;

    public Calendar toEntity(PostCalendarReq postCalendarReq) {
        Calendar calendar = Calendar.builder()
                .title(postCalendarReq.getTitle())
                .inform(postCalendarReq.getInform())
                .date(postCalendarReq.getDate())
                .build();

        return calendar;
    }
}
