package com.example.demo.theme.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Theme findByUserId(Long user);
}
