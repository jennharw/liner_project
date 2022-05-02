package com.example.demo.theme.controller;


import com.example.demo.config.BaseResponse;
import com.example.demo.config.exception.BaseException;
import com.example.demo.theme.dto.ThemeDeleteDto;
import com.example.demo.theme.dto.ThemeUpdateDto;
import com.example.demo.theme.dto.ThemeUpsertDto;
import com.example.demo.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theme")
public class ThemeController {
    private final ThemeService themeService;

    @PutMapping("/change-theme")
    public BaseResponse updateTheme(@RequestBody ThemeUpdateDto themeUpdateDto){
        themeService.updateTheme(themeUpdateDto);
        return new BaseResponse<>("OK");

    }


    @PostMapping("/user")
    public BaseResponse upsertTheme(@RequestBody ThemeUpsertDto themeUpsertDto){
        themeService.upsertTheme(themeUpsertDto);
        return new BaseResponse<>("OK");
    }

    @DeleteMapping
    public BaseResponse deleteTheme(@RequestBody ThemeDeleteDto themeDeleteDto) throws BaseException {
        themeService.deleteTheme(themeDeleteDto);
        return new BaseResponse<>("OK");
    }

}
