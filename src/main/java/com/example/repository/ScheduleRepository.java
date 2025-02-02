package com.example.repository;

import com.example.dto.schedule.ScheduleResponseDto;
import com.example.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules(int page, int size);
    List<ScheduleResponseDto> findSchedules(String writer, String updatedAt, int page, int size);
    int countSchedules();
    int countSchedulesByWriter(String writer, String updatedAt);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    Schedule findScheduleByIdWithPassword(Long scheduleId);
    Long findUserIdByScheduleId(Long scheduleId);
    int updateSchedule(Long scheduleId, Schedule schedule);
    int deleteSchedule(Long scheduleId);
};
