package com.example.demo.highlight.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PageRepository extends JpaRepository<LinerPage, Long> {

    @Query("select DISTINCT b from LinerPage b join fetch b.highlights where b.pageUrl= :url")
    Optional<LinerPage> findByPageUrl(@Param("url") String pageUrl);

//    Optional<Page> findByPageUrl(String pageUrl);
}
