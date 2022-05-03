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


//    private Long themeId;
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    private String color;

    private int orderOfColor; // 테마 내 색 순서

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "themeColor", cascade = {CascadeType.PERSIST, CascadeType.REMOVE })
//    private List<Highlight> highlights;
}
