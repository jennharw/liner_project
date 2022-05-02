package com.example.demo.highlight.domain;


import com.example.demo.highlight.dto.HighlightResponseDto;
import com.example.demo.theme.domain.ThemeColor;
import com.example.demo.user.domain.User;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Highlight extends AbstractEntity{

    //userid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page; // highlight 를 조회할때

    //현재 설정된 테마 내 색 중 하나 onetomany?
    @ManyToOne
    @JoinColumn(name = "themecolor_id")
    private ThemeColor color;

    @Column(length = 6000) //255 제한 해제
    private String text;

    private boolean IsDeleted;

    public HighlightResponseDto toDto() {
        return HighlightResponseDto.builder()
                .highlightId(id)
                .userId(user.getId())
                .pageId(page.getId())
                .colorHex(color.getColor())
                .text(text)
                .build();
    }
}
