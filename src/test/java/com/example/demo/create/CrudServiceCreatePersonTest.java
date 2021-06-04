package com.example.demo.create;

import com.example.demo.ParentTest;
import com.example.demo.config.ServiceMessages;
import com.example.demo.model.dto.request.PersonDto;
import com.example.demo.model.dto.request.PersonInsertRequestDto;
import com.example.demo.model.dto.request.SongDto;
import com.example.demo.model.dto.response.PersonResponseDto;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.entity.SongEntity;
import com.example.demo.model.repo.IPersonRepo;
import com.example.demo.model.repo.ISongRepo;
import com.example.demo.service.interfaces.ICrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CrudServiceCreatePersonTest extends ParentTest {
    private final ICrudService crudService;
    private final ISongRepo songRepo;
    private final IPersonRepo personRepo;

    @Autowired
    public CrudServiceCreatePersonTest(ICrudService crudService, ISongRepo songRepo, IPersonRepo personRepo) {
        this.crudService = crudService;
        this.songRepo = songRepo;
        this.personRepo = personRepo;
    }

    private List<Long> getTestSongsList() {
        return songRepo.findAll().stream().map(SongEntity::getSongId).collect(Collectors.toUnmodifiableList());
    }

    @Test
    void createPersonWithAllSongsInTheSystem() {
        long songCountBefore = songRepo.count();
        var personInsertRequestDto = new PersonInsertRequestDto(new PersonDto(null, TEST_NAME), getTestSongsList());
        insertNewPersonCorrectFlow(personInsertRequestDto);
        assertTrue(checkTheNumberOfSongs(songCountBefore));
    }

    @Test
    void createPersonWithSeveralSongsInTheSystem() {
        List<Long> testSongsList = getTestSongsList();
        long songCountBefore = songRepo.count();
        var personInsertRequestDto = new PersonInsertRequestDto(new PersonDto(null, TEST_NAME), testSongsList.subList(0, getRandomInt(0, testSongsList.size())));
        insertNewPersonCorrectFlow(personInsertRequestDto);
        assertTrue(checkTheNumberOfSongs(songCountBefore));
    }

    @Test
    void createPersonWithoutSongsInTheSystem() {
        long songCountBefore = songRepo.count();
        var personInsertRequestDto = new PersonInsertRequestDto(new PersonDto(null, TEST_NAME), null);
        insertNewPersonCorrectFlow(personInsertRequestDto);
        assertTrue(checkTheNumberOfSongs(songCountBefore));
    }

    @Test
    void createPersonWhoExistsInTheSystem() {
        long songCountBefore = songRepo.count();
        var personInsertRequestDtoFirstInsert = new PersonInsertRequestDto(new PersonDto(null, TEST_NAME), null);
        var personInsertRequestDtoSecondInsert = new PersonInsertRequestDto(new PersonDto(null, TEST_NAME), null);
        crudService.createPerson(personInsertRequestDtoFirstInsert);
        insertNewPersonNotCorrectFlow(personInsertRequestDtoSecondInsert, ServiceMessages.PERSON_WITH_PROVIDED_NAME_EXISTS_IN_DB);
        assertTrue(checkTheNumberOfSongs(songCountBefore));
    }

    @Test
    void createPersonIncorrectDto() {
        long songCountBefore = songRepo.count();
        var personInsertRequestDto = new PersonInsertRequestDto(new PersonDto(1L, TEST_NAME), null);
        insertNewPersonNotCorrectFlow(personInsertRequestDto, ServiceMessages.WRONG_FORMAT_OF_THE_INPUT_DTO);
        assertTrue(checkTheNumberOfSongs(songCountBefore));
    }

    private void insertNewPersonNotCorrectFlow(PersonInsertRequestDto personInsertRequestDto, ServiceMessages serviceMsg) {
        var resultMap = crudService.createPerson(personInsertRequestDto);
        assertEquals(1, resultMap.size());
        var msg = serviceMsg.getServiceMessage();
        assertTrue(resultMap.containsKey(msg));
        assertNull(resultMap.get(msg));
    }

    private boolean checkTheNumberOfSongs(long songCountBefore) {
        return songCountBefore == songRepo.count();
    }

    private void insertNewPersonCorrectFlow(PersonInsertRequestDto personInsertRequestDtoDto) {
        Map<String, PersonResponseDto> resultMap = crudService.createPerson(personInsertRequestDtoDto);
        assertEquals(1, resultMap.size());
        String serviceMessage = ServiceMessages.INSERTED.getServiceMessage();
        assertTrue(resultMap.containsKey(serviceMessage));

        PersonResponseDto personResponseDto = resultMap.get(serviceMessage);
        assertTrue(checkResponseAndRequestDto(personInsertRequestDtoDto, personResponseDto));

        assertTrue(checkResponseDtoAndDataInDb(personResponseDto));
    }

    private boolean checkResponseDtoAndDataInDb(PersonResponseDto personResponseDto) {
        Long personId = personResponseDto.getPersonId();
        PersonEntity personEntity = personRepo.findById(personId).orElseThrow();
        assertEquals(personResponseDto.getPersonName(), personEntity.getPersonName());
        List<SongDto> songCollection = personResponseDto.getSongCollection();
        if (songCollection != null) {
            assertTrue(
                    songCollection.stream().map(SongDto::getSongName).collect(Collectors.toUnmodifiableList()).
                            containsAll(personEntity.getSongSet().stream().map(SongEntity::getSongName).collect(Collectors.toUnmodifiableList()))
            );
        }
        return true;
    }

    private boolean checkResponseAndRequestDto(
            PersonInsertRequestDto personInsertRequestDtoDto, PersonResponseDto personResponseDto) {
        PersonDto personDto = personInsertRequestDtoDto.getPersonDto();
        assertNotNull(personResponseDto.getPersonId());
        assertEquals(personDto.getPersonName(), personResponseDto.getPersonName());
        List<SongDto> songCollection = personResponseDto.getSongCollection();
        List<Long> songIds = personInsertRequestDtoDto.getSongIds();
        if (songIds != null && !songIds.isEmpty()) {
            assertNotNull(songCollection);
            assertEquals(songIds.size(), songCollection.size());
        }
        return true;
    }
}