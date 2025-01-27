package com.example.repository;

import com.example.dto.schedule.ScheduleResponseDto;
import com.example.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findSchedulesByWriter(String writer);
    List<ScheduleResponseDto> findSchedulesByUpdatedAt(String updatedAt);
    Schedule findScheduleById(Long id);
    void deleteSchedule(Long id);
};
