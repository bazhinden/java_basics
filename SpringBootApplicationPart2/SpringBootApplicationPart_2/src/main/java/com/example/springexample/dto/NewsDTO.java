package com.example.springexample.dto;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsDTO {
    private Long id;
    private String title;
    private String text;
    private Instant date;
    private Long categoryId;

}
