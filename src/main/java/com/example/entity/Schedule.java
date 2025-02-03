package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long scheduleId;
    private String task;
    private Long userId;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(String task, Long userId, String password) {
        this.task = task;
        this.userId = userId;
        this.password = password;
    }

    public Schedule(String task, Long userId) {
        this.task = task;
        this.userId = userId;
    }
}
