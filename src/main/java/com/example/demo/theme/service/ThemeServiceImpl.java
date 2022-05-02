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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.config.BaseResponseStatus.BASETHEME;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final UserRepository userRepository;
    private final ThemeColorRepository themeColorRepository;
    private final ThemeRepository themeRepository;
    private final Map<Long, List<String>> colorMap = new HashMap<>();
    private final EntityManager entityManager;

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
        Theme theme = themeRepository.findByUserId(themeUpsertDto.getUserId());
//        TypedQuery<ThemeColor> themeColorTypedQuery =
//                entityManager.createQuery("SELECT s from ThemeColor s where s.id = ?", ThemeColor.class);
//        List<ThemeColor> themeColors = schoolQuery.getResultList();


        List<ThemeColor> themeColors = themeColorRepository.findAll();
        List<String> colors = List.of(themeUpsertDto.getTheme().getColors());
        int i = 0 ;
        for (ThemeColor themeColor : themeColors){
            themeColor.setColor(colors.get(i));
            i++;
            if (i >= colors.size()) {
                i = 0;
            }
        }

        if (!colors.isEmpty()) {
            for (String color:colors){
                ThemeColor themeColor = ThemeColor.builder()
                        .themeId(themeUpsertDto.getTheme().getId())
                        .color(color)
                        .build();
                entityManager.persist(themeColor);

            }

        }

        //em.flush(); //bulk
        //두가지 경우

        List<ThemeColor> themeColorList = themeColorRepository.findAll();
        if (colorMap.isEmpty()) {
            Map<Long, List<String>> colorMap =
            themeColorList.stream()
                    .collect(groupingBy(ThemeColor::getThemeId, mapping(ThemeColor::getColor, toList())));

        }

        List<List<String>> colorValues = null;
        colorMap.forEach(
                (key, value) ->
                        colorValues.add(value)
                               );



        if (colorValues.contains(colors)) {
            Long themeId = getKey(colorMap, colors);
            user.get().setTheme(Theme.builder().id(themeId).build());
        }
        //조합 중복안되게


        colorMap.put(themeUpsertDto.getTheme().getId(), colors);



    }

    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
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
            mycolor.setColor(colors.get(0));
//            i++;
//            if (i > colors.size()){
//                i = 0;
//            }
        }


        //}


        user.get().setTheme(Theme.builder().id(1L).build());
        // 보다 기존이 더 많을 때
        // 사용자 - theme - themecolor

    }


}
