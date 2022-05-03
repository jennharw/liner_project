package com.example.demo.theme.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThemeColorRepository extends JpaRepository<ThemeColor, Long> {
    List<ThemeColor> findByThemeId(Long themeId);

    Optional<ThemeColor> findByThemeIdAndOrderOfColor(Long ThemeId, int colorOrOrder);
}
