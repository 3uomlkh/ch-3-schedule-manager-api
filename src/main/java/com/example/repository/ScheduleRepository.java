package com.example.repository;

import com.example.dto.schedule.ScheduleResponseDto;
import com.example.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findSchedules(String writer, String updatedAt);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    void deleteSchedule(Long id);
};
