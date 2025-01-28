package com.example.controller;

import com.example.dto.schedule.ScheduleRequestDto;
import com.example.dto.schedule.ScheduleResponseDto;
import com.example.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ScheduleResponseDto>> findSchedules(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) String updatedAt
    ) {
        if (writer == null && updatedAt == null) {
            return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
        }
        return new ResponseEntity<>(scheduleService.findSchedules(writer, updatedAt), HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findSchedulesById(@PathVariable Long scheduleId){
        return new ResponseEntity<>(scheduleService.findScheduleById(scheduleId), HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestBody(required = false) ScheduleRequestDto dto
    ) {
        scheduleService.deleteSchedule(scheduleId, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

