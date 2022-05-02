package com.example.demo.highlight.dto;

import lombok.Getter;

@Getter
public class HighlightCreateDto {
    private Long userId;
    private String pageUrl;
    private String colorHex;
    private String text;
}
