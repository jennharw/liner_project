package com.example.demo.highlight.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HighlightResponseDto {
    private Long highlightId;
    private Long userId;
    private Long pageId;
    private String colorHex;
    private String text;

}
