package com.example.demo.highlight.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HighlightRepository extends JpaRepository<Highlight, Long> {

    @Modifying
    @Query("select h from Highlight h where h.linerPage = ?1 and h.user = ?2 and h.IsDeleted = FALSE order by h.updatedAt DESC ")
    Page<Highlight> findHighlight(Long userId, Long page, Pageable pageable);

    @Query("select h from Highlight h where h.user = ?1 and h.IsDeleted = FALSE order by h.updatedAt DESC ")
    List<Highlight> findAllByUserIdAndIsDeletedIsFalseAndOrderByUpdatedAtDesc(Pageable pageable, Long userId);
}
