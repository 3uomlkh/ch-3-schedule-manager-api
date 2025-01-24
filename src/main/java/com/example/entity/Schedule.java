package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long scheduleId;
    private String task;
    private String writer;
    private String password;
    private String createdAt;
    private String updatedAt;
}
