package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long scheduleId;
    private String task;
    private String writer;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(String task, String writer,String password) {
        this.task = task;
        this.writer = writer;
        this.password = password;
    }
}
