package com.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Paging<T> {
    private List<T> content;
    private int totalPages;
    private int totalElements;
    private int size;
    private int number;
}
