package com.example.service;

import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;
import com.example.entity.Schedule;
import com.example.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public List<ScheduleResponseDto> findSchedules(String writer, String updatedAt) {
        return scheduleRepository.findSchedules(writer, updatedAt);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        return scheduleRepository.findScheduleById(scheduleId);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto dto) {
        Schedule current = scheduleRepository.findScheduleByIdWithPassword(scheduleId);
        if (!current.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password.");
        }

        if (dto.getTask() == null || dto.getWriter() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The task and writer are required values.");
        }

        Schedule schedule = new Schedule(dto.getTask(), dto.getWriter(), dto.getPassword());
        int updatedRow = scheduleRepository.updateSchedule(scheduleId, schedule);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist schedule");
        }

        return scheduleRepository.findScheduleById(scheduleId);
    }

    @Override
    public void deleteSchedule(Long id) {

    }
}
