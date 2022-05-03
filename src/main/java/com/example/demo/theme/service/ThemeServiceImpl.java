package com.example.demo.theme.service;

import com.example.demo.config.exception.BaseException;
import com.example.demo.theme.domain.Theme;
import com.example.demo.theme.domain.ThemeColor;
import com.example.demo.theme.domain.ThemeColorRepository;
import com.example.demo.theme.domain.ThemeRepository;
import com.example.demo.theme.dto.ThemeDeleteDto;
import com.example.demo.theme.dto.ThemeUpdateDto;
import com.example.demo.theme.dto.ThemeUpsertDto;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.BASETHEME;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final UserRepository userRepository;
    private final ThemeColorRepository themeColorRepository;
    private final ThemeRepository themeRepository;
    private Map<Theme, Set<String>> colorMap = new HashMap<>();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void updateTheme(ThemeUpdateDto themeUpdateDto) {
        Optional<User> user = userRepository.findById(themeUpdateDto.getUserId());
        Optional<Theme> theme = themeRepository.findById(themeUpdateDto.getThemeId());

        user.get().setTheme(theme.get());

    }

    @Override
    @Transactional
    public void upsertTheme(ThemeUpsertDto themeUpsertDto) {
        Optional<User> user = userRepository.findById(themeUpsertDto.getUserId());
        Optional<Theme> theme = themeRepository.findByUsers(user.get());
//        TypedQuery<ThemeColor> themeColorTypedQuery =
//                entityManager.createQuery("SELECT s from ThemeColor s where s.id = ?", ThemeColor.class);
//        List<ThemeColor> themeColors = schoolQuery.getResultList();

        List<Set<String>> colorValues = new ArrayList<>();
        if (colorMap.isEmpty()) {
            List<ThemeColor> themeColorList = themeColorRepository.findAll();
            colorMap = themeColorList.stream()
                    .collect(groupingBy(ThemeColor::getTheme, mapping(ThemeColor::getColor, toSet())));

        }
        colorMap.forEach(
                (key, value) ->
                        colorValues.add(value)
        );
//        System.out.println(colorMap);
        Set<String> colors_set = Set.of(themeUpsertDto.getTheme().getColors());

        if (colorValues.contains(colors_set)) {
            Long themeId = getKey(colorMap, colors_set);
            user.get().setTheme(Theme.builder().id(themeId).build());
        } else{

            List<ThemeColor> themeColors = themeColorRepository.findByThemeId(theme.get().getId());
            List<String> colors = List.of(themeUpsertDto.getTheme().getColors());
            int i = 0 ;
            int j = 0 ;
            for (ThemeColor themeColor : themeColors){
                themeColor.setColor(colors.get(i));
                i++;
                j++;
                if (i >= colors.size()) {
                    i = 0;
                }
            } //bulk update ?

            j++;
            if (j < colors.size()) {
                for (int k = j; k <= colors.size();k++){

                    ThemeColor themeColor = ThemeColor.builder()
                            .theme(theme.get())
                            .orderOfColor(k)
                            .color(colors.get(k-1))
                            .build();

                    entityManager.persist(themeColor);
                }
            }
            //em.flush(); //bulk insert
            //두가지 경우
            colorMap.put(Theme.builder().id(themeUpsertDto.getTheme().getId()).build(), colors_set);

        }
        //조합 중복안되게


    }

    public <K, V> Long getKey(Map<Theme, Set<String>> map, Set<String> value) {
        for (Map.Entry<Theme, Set<String>> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey().getId();
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteTheme(ThemeDeleteDto themeDeleteDto) throws BaseException {
        Optional<User> user = userRepository.findById(themeDeleteDto.getUserId());
        Theme theme = user.get().getTheme();

        if (theme.getId().equals(0) || theme.getId().equals(1)){
            throw new BaseException(BASETHEME);
        }

        List<ThemeColor> myColors = themeColorRepository.findByThemeId(theme.getId());
        List<ThemeColor> defaultThemeColors = themeColorRepository.findByThemeId(1L);

        List<String> colors = defaultThemeColors.stream().map(ThemeColor::getColor).collect(Collectors.toList());
        int i = 0;
        //if (myColors.size() > defaultThemeColors.size()){

        for (ThemeColor mycolor : myColors){

            if (i > colors.size()){
                mycolor.setColor(colors.get(0));
            }else{
                mycolor.setColor(colors.get(i));
                i++;
            }
        }

        //}


        //user.get().setTheme(Theme.builder().id(1L).build());
        // 보다 기존이 더 많을 때
        // 사용자 - theme - themecolor

    }


}
