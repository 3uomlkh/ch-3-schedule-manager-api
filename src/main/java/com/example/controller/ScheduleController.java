package com.example.controller;

import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;
import com.example.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {
        return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
    }

    @GetMapping("/writer")
    public ResponseEntity<List<ScheduleResponseDto>> findSchedulesByWriter(
            @RequestParam(required = false) String writer
    ) {
        return new ResponseEntity<>(scheduleService.findSchedulesByWriter(writer), HttpStatus.OK);
    }

    @GetMapping("/updatedAt")
    public ResponseEntity<List<ScheduleResponseDto>> findSchedulesByUpdatedAt(
            @RequestParam(required = false) String updatedAt
    ) {
        return new ResponseEntity<>(scheduleService.findSchedulesByUpdatedAt(updatedAt), HttpStatus.OK);
    }
}

