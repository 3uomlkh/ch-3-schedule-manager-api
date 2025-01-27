package com.example.service;

import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;
import com.example.entity.Schedule;
import com.example.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTask(), dto.getWriter(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByWriter(String writer) {
        return scheduleRepository.findSchedulesByWriter(writer);
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
