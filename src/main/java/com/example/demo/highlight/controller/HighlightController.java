package com.example.demo.highlight.controller;

import com.example.demo.config.BaseResponse;
import com.example.demo.config.exception.BaseException;
import com.example.demo.highlight.dto.*;
import com.example.demo.highlight.service.HighlightService;
import com.example.demo.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/highlight")
public class HighlightController {
    private final HighlightService highlightService;




    @PostMapping
    public HighlightResponseDto createHighlight(@RequestBody HighlightCreateDto highlightCreateDto) throws BaseException {
        return highlightService.saveHighlight(highlightCreateDto);
    }

    @PutMapping
    public HighlightResponseDto updateHighlight(@RequestBody HighlightUpdateDto highlightUpdateDto){
        return highlightService.updateHighlight(highlightUpdateDto);
    }

    @GetMapping("/page")
    public List<HighlightResponseDto> readHighlightInPage(@RequestBody HighlightReadInPageDto highlightReadDto, Pageable pageable){
        return highlightService.readHighlightInPage(highlightReadDto, pageable);
    }

    @GetMapping("/")
    public List<HighlightReadResponseDto> readHighlight(@RequestBody HighlightReadDto highlightReadDto, Pageable pageable){
        return highlightService.readHighlight(highlightReadDto, pageable);
    }

    @PutMapping("/not")
    public BaseResponse deleteHighlight(@RequestBody HighlightDeleteDto highlightDeleteDto){
        highlightService.deleteHighlight(highlightDeleteDto);
        return new BaseResponse<>("ok");
    }
}
