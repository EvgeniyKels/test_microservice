package com.example.demo.integration_test.receive;

import com.example.demo.integration_test.ParentTest;
import com.example.demo.config.constants.ServiceMessages;
import com.example.demo.model.dto.request.SongDto;
import com.example.demo.model.dto.response.PersonResponseDto;
import com.example.demo.model.dto.response.SongResponseDto;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.entity.SongEntity;
import com.example.demo.model.repo.IPersonRepo;
import com.example.demo.model.repo.ISongRepo;
import com.example.demo.service.interfaces.ICrudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CrudServiceGetPersonTest extends ParentTest {

    private final ISongRepo songRepo;
    private final ICrudService crudService;
    private final IPersonRepo personRepo;

    @Autowired
    public CrudServiceGetPersonTest(ISongRepo songRepo, ICrudService crudService, IPersonRepo personRepo) {
        this.songRepo = songRepo;
        this.crudService = crudService;
        this.personRepo = personRepo;
    }

    @Test
    void getAllPerson() {
        Map<String, List<SongResponseDto>> songsResponse = crudService.getAllSongs();
        assertEquals(1, songsResponse.size());
        String resultMsg = ServiceMessages.RESULT.getServiceMessage();
        assertTrue(songsResponse.containsKey(resultMsg));
        List<SongResponseDto> songResponseDtos = songsResponse.get(resultMsg);
        assertEquals(songRepo.count(), songResponseDtos.size());
    }

    @Test
    void getPersonsBySong() {
        List<Long> oneRandomSong = getRandomSongs(1);
        doTest(oneRandomSong);
    }

    @Test
    void getPersonsBySeveralSong() {
        List<Long> songIds = getRandomSongs(getRandomInt(1, (int) songRepo.count()));
        doTest(songIds);
    }

    @Test
    void getPersonsByNotExistingSeveralSong() {
        List<Long> songIds = List.of(100200L, 200100L);
        Map<String, List<PersonResponseDto>> personsBySong = crudService.getPersonsBySong(songIds);
        String resultMsg = checkResultMapFormat(personsBySong);
        assertEquals(0, personsBySong.get(resultMsg).size());
    }

    private String checkResultMapFormat(Map<String, List<PersonResponseDto>> personsBySong) {
        assertEquals(1, personsBySong.size());
        String resultMsg = ServiceMessages.RESULT.getServiceMessage();
        assertTrue(personsBySong.containsKey(resultMsg));
        assertNotNull(personsBySong.get(resultMsg));
        return resultMsg;
    }

    private void doTest(List<Long> songIds) {
        Map<String, List<PersonResponseDto>> personsBySong = crudService.getPersonsBySong(songIds);

        String resultMsg = checkResultMapFormat(personsBySong);

        for (Long id : songIds) {
            SongEntity songEntity = songRepo.findById(id).orElseThrow();

            List<PersonResponseDto> personResponseDtos = personsBySong.get(resultMsg);
            for (PersonResponseDto pRd: personResponseDtos) {
                assertTrue(
                        songEntity.getPersonSet().stream().
                                map(PersonEntity::getPersonId).
                                collect(Collectors.toUnmodifiableList()).contains(pRd.getPersonId()));
                assertTrue(
                        pRd.getSongCollection().stream().
                                map(SongDto::getSongId).
                                collect(Collectors.toUnmodifiableList()).contains(songEntity.getSongId()));

                assertTrue(personRepo.findById(pRd.getPersonId()).orElseThrow().getSongSet().contains(songEntity));
            }
        }
    }

    private List<Long> getRandomSongs(int numberOfSongs) {
        List<Long> allIds = songRepo.findAll().stream().map(SongEntity::getSongId).collect(Collectors.toUnmodifiableList());
        Set<Long>songIds = new HashSet<>();
        for (int i = 0; i < numberOfSongs; i++) {
            songIds.add(allIds.get(getRandomInt(1, (int) songRepo.count())));
        }
        return List.copyOf(songIds);
    }
}

