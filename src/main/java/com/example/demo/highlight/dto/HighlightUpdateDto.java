package com.example.demo.highlight.dto;

import lombok.Getter;

@Getter
public class HighlightUpdateDto {
    private Long highlightId;
    private Long userId;
    private String colorHex;
    private String text;

}
