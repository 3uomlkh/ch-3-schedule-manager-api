package com.example.service;

import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findSchedulesByWriter(String writer);
    List<ScheduleResponseDto> findSchedulesByUpdatedAt(String updatedAt);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    ScheduleResponseDto updateSchedule(Long id, String task, String writer);
    void deleteSchedule(Long id);
}
