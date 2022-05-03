package com.example.demo.highlight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
public class HighlightReadResponseDto {
    private Long pageId;

    private String pageUrl;

    private List<HighlightResponseDto> highlights;
}
