package com.example.dto.schedule;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long scheduleId;
    private String task;
    private String writer;
    private String createdAt;
    private String updatedAt;
}
