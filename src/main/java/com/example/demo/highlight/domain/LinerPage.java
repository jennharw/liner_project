package com.example.demo.highlight.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class LinerPage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String pageUrl;

    //OneToMany
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "linerPage", cascade = {CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Highlight> highlights;
}
