package com.example.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long scheduleId;
    private String task;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
