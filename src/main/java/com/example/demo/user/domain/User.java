package com.example.demo.user.domain;

import com.example.demo.highlight.domain.Highlight;
import com.example.demo.theme.domain.Theme;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id ;

    private String userId;

    private String password;

    private String nickname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Highlight> highlights;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;
}
