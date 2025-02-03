package com.example.service;

import com.example.Paging;
import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;
import com.example.entity.Schedule;
import com.example.entity.User;
import com.example.repository.ScheduleRepository;
import com.example.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        User existingUser;
        try {
            existingUser = userRepository.findUserByNameAndPassword(dto.getWriter(), dto.getPassword());
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("error"); // TODO: 에러메시지 수정
        }

        Schedule schedule = new Schedule(dto.getTask(), existingUser.getUserId(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public Paging<ScheduleResponseDto> findAllSchedules(int page, int size) {
        List<ScheduleResponseDto> schedules = scheduleRepository.findAllSchedules(page, size);
        int totalElements = scheduleRepository.countSchedules();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new Paging<>(schedules, totalPages, totalElements, size, page);
    }

    @Override
    public Paging<ScheduleResponseDto> findSchedules(String writer, String updatedAt, int page, int size) {
        List<ScheduleResponseDto> schedules = scheduleRepository.findSchedules(writer, updatedAt, page, size);
        int totalElements = scheduleRepository.countSchedulesByWriter(writer, updatedAt);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new Paging<>(schedules, totalPages, totalElements, size, page);
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

        if (dto.getTask() == null && dto.getWriter() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The task and writer are required values.");
        }

        Long userId = scheduleRepository.findUserIdByScheduleId(scheduleId);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The writer does not exist.");
        }

        Schedule schedule = new Schedule(dto.getTask(), userId, dto.getPassword());
        int updatedRow = scheduleRepository.updateSchedule(scheduleId, schedule);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The schedule does not exist.");
        }

        return scheduleRepository.findScheduleById(scheduleId);
    }

    @Override
    public void deleteSchedule(Long scheduleId, String password) {
        Schedule current = scheduleRepository.findScheduleByIdWithPassword(scheduleId);
        if (!current.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password.");
        }

        int deleteRow = scheduleRepository.deleteSchedule(scheduleId);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist schedule");
        }
    }
}
