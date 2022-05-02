package com.example.demo.highlight.service;

import com.example.demo.config.exception.BaseException;
import com.example.demo.highlight.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HighlightService {
    HighlightResponseDto saveHighlight(HighlightCreateDto highlightCreateDto) throws BaseException;

    HighlightResponseDto updateHighlight(HighlightUpdateDto highlightUpdateDto);

    List<HighlightResponseDto> readHighlightInPage(HighlightReadInPageDto highlightReadDto, Pageable pageable);

    List<HighlightReadResponseDto> readHighlight(HighlightReadDto highlightReadDto, Pageable pageable);

    void deleteHighlight(HighlightDeleteDto highlightDeleteDto);
}
