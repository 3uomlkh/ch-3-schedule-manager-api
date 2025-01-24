package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String task;
    private String writer;
    private String createdAt;
    private String updatedAt;
}
