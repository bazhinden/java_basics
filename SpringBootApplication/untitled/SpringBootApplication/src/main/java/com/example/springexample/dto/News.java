package com.example.springexample.dto;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class News {
    private Long id;
    private String title;
    private String text;
    private Instant date;
}
