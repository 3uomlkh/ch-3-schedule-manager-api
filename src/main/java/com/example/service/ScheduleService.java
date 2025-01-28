package com.example.service;

import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findSchedules(String writer, String updatedAt);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto dto);
    void deleteSchedule(Long id);
}
