package com.example.demo.highlight.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {
    Optional<Page> findByPageUrl(String pageUrl);
}
