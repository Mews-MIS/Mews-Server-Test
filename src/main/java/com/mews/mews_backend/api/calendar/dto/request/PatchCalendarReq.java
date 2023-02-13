package com.mews.mews_backend.api.calendar.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mews.mews_backend.domain.calendar.entity.Calendar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatchCalendarReq {

    @NotNull
    @ApiModelProperty(notes = "id", example = "1")
    private Integer id;

    @NotBlank
    @ApiModelProperty(notes = "제목", example = "수강 신청 사전 선택")
    private String title;

    @NotBlank
    @ApiModelProperty(name = "종류", example = "동국대학교")
    private String category;

    @ApiModelProperty(notes = "날짜(년-월-일)", example = "2023-02-06")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @ApiModelProperty(notes = "날짜(년-월-일)", example = "2023-02-06")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public Calendar toEntity(PatchCalendarReq patchCalendarReq) {
        Calendar calendar = Calendar.builder()
                .title(patchCalendarReq.getTitle())
                .category(patchCalendarReq.getCategory())
                .startDate(patchCalendarReq.getStartDate())
                .endDate(patchCalendarReq.getEndDate())
                .build();

        return calendar;
    }
}
