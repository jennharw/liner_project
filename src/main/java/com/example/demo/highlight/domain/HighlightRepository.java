package com.example.demo.highlight.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HighlightRepository extends JpaRepository<Highlight, Long> {
    List<Highlight> findAllByUserIdAndByPageIdAndIsDeletedIsFalseOrderByUpdatedAtDesc(Pageable pageable, Long userId, Long pageId);

    List<Highlight> findAllByUserIdAndIsDeletedIsFalseAndOrderByUpdatedAtDesc(Pageable pageable, Long userId);
}
