package com.example.demo.highlight.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class HighlightReadResponseDto {
    private Long pageId;

    private String pageUrl;

    private List<HighlightResponseDto> highlights;
}
