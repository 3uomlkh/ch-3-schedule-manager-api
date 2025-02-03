package com.example.service;

import com.example.Paging;
import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    Paging<ScheduleResponseDto> findAllSchedules(int page, int size);
    Paging<ScheduleResponseDto> findSchedules(String writer, String updatedAt, int page, int size);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto dto);
    void deleteSchedule(Long scheduleId, String password);
}
