package com.example.demo.theme.dto;

import lombok.Getter;

@Getter
public class ThemeUpsertDto {
    private Long userId;
    private ThemeDto theme;

    @Getter
    public class ThemeDto {
        private Long Id;
        private String[] colors;
    }

}
