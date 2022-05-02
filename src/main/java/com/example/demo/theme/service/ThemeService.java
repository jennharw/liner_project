package com.example.demo.theme.service;

import com.example.demo.config.exception.BaseException;
import com.example.demo.theme.dto.ThemeDeleteDto;
import com.example.demo.theme.dto.ThemeUpsertDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ThemeService {


    void upsertTheme(ThemeUpsertDto themeUpsertDto);

    void deleteTheme(ThemeDeleteDto themeDeleteDto) throws BaseException;
}
