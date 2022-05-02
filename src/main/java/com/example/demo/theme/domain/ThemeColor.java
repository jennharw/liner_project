package com.example.demo.theme.domain;

import com.example.demo.highlight.domain.Highlight;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class ThemeColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long themeId;

    private String color;

    private int order; // 테마 내 색 순서

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "highlight", cascade = {CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Highlight> highlights;
}
