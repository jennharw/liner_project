package com.example.demo.highlight.service;

import com.example.demo.config.exception.BaseException;
import com.example.demo.highlight.domain.Highlight;
import com.example.demo.highlight.domain.HighlightRepository;
import com.example.demo.highlight.domain.LinerPage;
import com.example.demo.highlight.domain.PageRepository;
import com.example.demo.highlight.dto.*;
import com.example.demo.theme.domain.Theme;
import com.example.demo.theme.domain.ThemeColor;
import com.example.demo.theme.domain.ThemeColorRepository;
import com.example.demo.theme.domain.ThemeRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.COLORNOTTHEMECOLOR;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class HighlightServiceImpl implements HighlightService {
    private final HighlightRepository highlightRepository;
    private final UserRepository userRepository;
    private final PageRepository pageRepository;
    private final ThemeRepository themeRepository;
    private final ThemeColorRepository themeColorRepository;

    @Override
    @Transactional
    public HighlightResponseDto saveHighlight(HighlightCreateDto highlightCreateDto) throws BaseException{

        Optional<User> user = userRepository.findById(highlightCreateDto.getUserId());

        Optional<LinerPage> page = pageRepository.findByPageUrl(highlightCreateDto.getPageUrl());

        LinerPage userpage = null;
        if (page.isEmpty()) {
            LinerPage page_ = LinerPage.builder().pageUrl(highlightCreateDto.getPageUrl()).build();
            userpage = pageRepository.save(page_);
        }else {
            userpage = page.get();
        }

        Theme theme = themeRepository.findByUserId(user.get().getId());
        List<ThemeColor> themeColors = themeColorRepository.findByThemeId(theme.getId());
        if (themeColors.stream().map(ThemeColor::getColor).collect(toList()).contains(highlightCreateDto.getColorHex())){

            Optional<ThemeColor> userThemeColors = themeColors.stream().filter(themeColor -> themeColor.getColor().equals(
                    highlightCreateDto.getColorHex()
            )).findFirst();

            Highlight highlight = Highlight.builder()
                    .user(user.get())
                    .linerPage(userpage)
                    .color(userThemeColors.get())
                    .text(highlightCreateDto.getText())
                    .build();

            return highlight.toDto();
        }else{
            throw new BaseException(COLORNOTTHEMECOLOR);
        }




    }


    @Override
    @Transactional
    public HighlightResponseDto updateHighlight(HighlightUpdateDto highlightUpdateDto){
        Optional<Highlight> highlight = highlightRepository.findById(highlightUpdateDto.getHighlightId());
        Optional<User> user = userRepository.findById(highlightUpdateDto.getUserId());

        if (!highlightUpdateDto.getText().isEmpty()) {
            highlight.get().setText(highlightUpdateDto.getText());
        }
        if (!highlightUpdateDto.getColorHex().isEmpty()){
            Theme theme = themeRepository.findByUserId(user.get().getId());
            List<ThemeColor> themeColors = themeColorRepository.findByThemeId(theme.getId());

            if (themeColors.stream().map(ThemeColor::getColor).collect(toList()).contains(highlightUpdateDto.getColorHex())){
                Optional<ThemeColor> userThemeColors = themeColors.stream().filter(themeColor -> themeColor.getColor().equals(
                        highlightUpdateDto.getColorHex()
                )).findFirst();

                highlight.get().setColor(userThemeColors.get());

            }
        }

        return highlight.get().toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HighlightResponseDto> readHighlightInPage(HighlightReadInPageDto highlightReadDto, Pageable pageable) {
        Optional<LinerPage> page = pageRepository.findByPageUrl(highlightReadDto.getPageUrl());
        Page<Highlight> highlights = highlightRepository.findHighlight(highlightReadDto.getUserId(), page.get().getId() ,pageable);
        return highlights.stream()
                .map(Highlight::toDto).collect(toList());
    }

    @Override
    @Transactional
    public List<HighlightReadResponseDto> readHighlight(HighlightReadDto highlightReadDto, Pageable pageable) {
        List<Highlight> highlights = highlightRepository.findAllByUserIdAndIsDeletedIsFalseAndOrderByUpdatedAtDesc(pageable, highlightReadDto.getUserId());

        //
        Map<LinerPage, List<HighlightResponseDto>> highlightPerPage =
                highlights.stream()
                //.map(Highlight::toDto)
                    .collect(groupingBy(Highlight::getLinerPage, mapping(Highlight::toDto, toList())))
                    ;
        List<HighlightReadResponseDto> highlightReadResponseDtos = null;
        highlightPerPage.forEach(
                (key, value) ->
                        highlightReadResponseDtos.add(
                                HighlightReadResponseDto.builder()
                                        .pageId(key.getId())
                                        .pageUrl(key.getPageUrl())
                                        .highlights(value)
                                        .build()
                        ));
        
        return highlightReadResponseDtos;
    }

    @Override
    public void deleteHighlight(HighlightDeleteDto highlightDeleteDto) {
        Optional<Highlight> highlight = highlightRepository.findById(highlightDeleteDto.getHighlightId());
        highlight.get().setIsDeleted(true);
    }


}
