package com.example.demo.util;

import com.example.demo.model.dto.response.PersonResponseDto;
import com.example.demo.model.dto.response.SongResponseDto;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.entity.SongEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityToDtoMapper {

    public static List<PersonResponseDto> mapPersonEntityListToPersonDtoList(List<PersonEntity> personEntityList) {
        return personEntityList.stream().
                map(x -> new PersonResponseDto(x, x.getSongSet())).collect(Collectors.toUnmodifiableList());
    }

    public static List<SongResponseDto> mapSongEntityListToSongDtoList (List<SongEntity> songEntityList) {
        return songEntityList.stream().map(x -> new SongResponseDto(x, x.getPersonSet())).collect(Collectors.toUnmodifiableList());
    }

    public static <K> Map<String, K> getResultMap(String msg, K k) {
        Map<String, K> resultMap = new HashMap<>(1, 1);
        resultMap.put(msg, k);
        return Collections.unmodifiableMap(resultMap);
    }
}
