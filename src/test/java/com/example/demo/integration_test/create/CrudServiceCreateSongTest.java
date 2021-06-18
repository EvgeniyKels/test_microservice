package com.example.demo.integration_test.create;

import com.example.demo.integration_test.ParentTest;
import com.example.demo.config.constants.ServiceMessages;
import com.example.demo.model.dto.request.PersonDto;
import com.example.demo.model.dto.request.SongDto;
import com.example.demo.model.dto.request.SongInsertRequestDto;
import com.example.demo.model.dto.response.SongResponseDto;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.entity.SongEntity;
import com.example.demo.model.repo.IPersonRepo;
import com.example.demo.model.repo.ISongRepo;
import com.example.demo.service.interfaces.ICrudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CrudServiceCreateSongTest extends ParentTest {

    private final ICrudService crudService;
    private final ISongRepo songRepo;
    private final IPersonRepo personRepo;

    @Autowired
    public CrudServiceCreateSongTest(ICrudService crudService, ISongRepo songRepo, IPersonRepo personRepo) {
        this.crudService = crudService;
        this.songRepo = songRepo;
        this.personRepo = personRepo;
    }

    @Test
    void createSongWithAllPersonsInTheSystem() {
        long personCountBefore = personRepo.count();
        var songInsertRequestDto = new SongInsertRequestDto(new SongDto(null, TEST_NAME), getPersonsTestIds());
        insertNewSongCorrectFlow(songInsertRequestDto);
        assertTrue(checkTheNumberOfPersons(personCountBefore));
    }

    private List<Long> getPersonsTestIds() {
        return personRepo.findAll().stream().map(PersonEntity::getPersonId).collect(Collectors.toUnmodifiableList());
    }

    @Test
    void createSongWithSeveralSongsInTheSystem() {
        List<Long> personsTestIds = getPersonsTestIds();
        long personCountBefore = personRepo.count();
        var songInsertRequestDto = new SongInsertRequestDto(new SongDto(null, TEST_NAME), personsTestIds.subList(0, getRandomInt(0, personsTestIds.size())));
        insertNewSongCorrectFlow(songInsertRequestDto);
        assertTrue(checkTheNumberOfPersons(personCountBefore));
    }

    @Test
    void createSongWithoutSongsInTheSystem() {
        long personCountBefore = personRepo.count();
        var songInsertRequestDto = new SongInsertRequestDto(new SongDto(null, TEST_NAME), null);
        insertNewSongCorrectFlow(songInsertRequestDto);
        assertTrue(checkTheNumberOfPersons(personCountBefore));
    }

    @Test
    void createSongWhichExistsInTheSystem() {
        long songCountBefore = personRepo.count();
        var songInsertRequestDtoFirstInsert = new SongInsertRequestDto(new SongDto(null, TEST_NAME), null);
        var songInsertRequestDto = new SongInsertRequestDto(new SongDto(null, TEST_NAME), null);
        crudService.createSong(songInsertRequestDtoFirstInsert);
        insertNewSongNotCorrectFlow(songInsertRequestDto, ServiceMessages.SONG_WITH_PROVIDED_NAME_EXISTS_IN_DB);
        assertTrue(checkTheNumberOfPersons(songCountBefore));
    }

    @Test
    void createSongIncorrectDto() {
        long songCountBefore = personRepo.count();
        var songInsertRequestDto = new SongInsertRequestDto(new SongDto(1L, TEST_NAME), null);
        insertNewSongNotCorrectFlow(songInsertRequestDto, ServiceMessages.WRONG_FORMAT_OF_THE_INPUT_DTO);
        assertTrue(checkTheNumberOfPersons(songCountBefore));
    }

    private void insertNewSongNotCorrectFlow(SongInsertRequestDto songInsertRequestDto, ServiceMessages serviceMsg) {
        var resultMap = crudService.createSong(songInsertRequestDto);
        assertEquals(1, resultMap.size());
        var msg = serviceMsg.getServiceMessage();
        assertTrue(resultMap.containsKey(msg));
        assertNull(resultMap.get(msg));
    }

    private boolean checkTheNumberOfPersons(long personCountBefore) {
        return personCountBefore == personRepo.count();
    }

    private void insertNewSongCorrectFlow(SongInsertRequestDto songInsertRequestDto) {
        Map<String, SongResponseDto> resultMap = crudService.createSong(songInsertRequestDto);
        assertEquals(1, resultMap.size());
        String serviceMessage = ServiceMessages.INSERTED.getServiceMessage();
        assertTrue(resultMap.containsKey(serviceMessage));

        SongResponseDto personResponseDto = resultMap.get(serviceMessage);
        assertTrue(checkResponseAndRequestDto(songInsertRequestDto, personResponseDto));

        assertTrue(checkResponseDtoAndDataInDb(personResponseDto));
    }

    private boolean checkResponseDtoAndDataInDb(SongResponseDto songResponseDto) {
        Long songId = songResponseDto.getSongId();
        SongEntity songEntity = songRepo.findById(songId).orElseThrow();
        assertEquals(songResponseDto.getSongName(), songEntity.getSongName());
        List<PersonDto> personCollection = songResponseDto.getPersonCollection();
        if (personCollection != null) {
            assertTrue(
                    personCollection.stream().map(PersonDto::getPersonName).collect(Collectors.toUnmodifiableList()).
                            containsAll(songEntity.getPersonSet().stream().map(PersonEntity::getPersonName).collect(Collectors.toUnmodifiableList()))
            );
        }
        return true;
    }

    private boolean checkResponseAndRequestDto(
            SongInsertRequestDto songInsertRequestDtoDto, SongResponseDto songResponseDto) {
        SongDto songDto = songInsertRequestDtoDto.getSongDto();
        assertNotNull(songResponseDto.getSongId());
        assertEquals(songDto.getSongName(), songResponseDto.getSongName());
        List<PersonDto> personCollection = songResponseDto.getPersonCollection();
        List<Long> personIds = songInsertRequestDtoDto.getPersonIds();
        if (personIds != null && !personIds.isEmpty()) {
            assertNotNull(personCollection);
            assertEquals(personIds.size(), personCollection.size());
        }
        return true;
    }
}