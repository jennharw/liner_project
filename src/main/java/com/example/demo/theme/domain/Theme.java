package com.example.demo.theme.domain;

import com.example.demo.highlight.domain.Highlight;
import com.example.demo.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Theme {
    //id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "theme", cascade = {CascadeType.PERSIST, CascadeType.REMOVE })
    private List<User> userId; //OnetoMany


}
