package com.example.service;

import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        return null;
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return List.of();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        return null;
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String task, String writer) {
        return null;
    }

    @Override
    public void deleteSchedule(Long id) {

    }
}
