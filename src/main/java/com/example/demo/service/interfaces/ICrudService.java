package com.example.demo.service.interfaces;

import com.example.demo.model.dto.request.PersonInsertRequestDto;
import com.example.demo.model.dto.response.PersonResponseDto;
import com.example.demo.model.dto.request.SongInsertRequestDto;
import com.example.demo.model.dto.response.SongResponseDto;

import java.util.List;
import java.util.Map;

public interface ICrudService {
    Map<String, PersonResponseDto> createPerson(PersonInsertRequestDto personDto);
    Map<String, SongResponseDto> createSong(SongInsertRequestDto songDTO);

    Map<String, List<PersonResponseDto>> getPersonsBySong(List<Long> songIds);
    Map<String, List<SongResponseDto>> getSongsByPerson(List<Long> personIds);
    Map<String, List<PersonResponseDto>> getAllPersons();
    Map<String, List<SongResponseDto>> getAllSongs();

    Map<String, List<PersonResponseDto>> updatePerson(List<Integer>personIds, List<Integer>songIds);
    Map<String, List<SongResponseDto>> updateSong(List<Integer>songIds, List<Integer>personIds);

    Map<String, Long> deletePerson(List<Long>personIds);
    Map<String, Long> deleteSong(List<Long>songIds);
}
