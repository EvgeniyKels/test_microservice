package com.example.demo.controller;

import com.example.demo.model.dto.request.DeletePerson;
import com.example.demo.model.dto.request.UpdatePerson;
import com.example.demo.model.dto.request.PersonInsertRequestDto;
import com.example.demo.model.dto.request.SongInsertRequestDto;
import com.example.demo.service.interfaces.ICrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.constants.ControllerConstants.*;

@RestController
@RequestMapping(CRUD)
public class HttpController extends AbstractHttpController{
    private ICrudService crudService;

    @PostMapping(PERSON)
    public ResponseEntity<ResBody> createPerson(PersonInsertRequestDto personDto) {
        return prepareResponseBody(crudService.createPerson(personDto));
    }

    @PostMapping(SONG)
    public ResponseEntity<ResBody> createSong(SongInsertRequestDto songDTO) {
        return prepareResponseBody(crudService.createSong(songDTO));
    }

    @GetMapping(PERSON_SONG)
    public ResponseEntity<ResBody> getPersonsBySong(List<Long> songIds) {
        return prepareResponseBody(crudService.getPersonsBySong(songIds));
    }

    @GetMapping(SONG_PERSON)
    public ResponseEntity<ResBody> getSongsByPerson(List<Long> personIds) {
        return prepareResponseBody(crudService.getSongsByPerson(personIds));
    }

    @GetMapping(PERSONS)
    public ResponseEntity<ResBody> getAllPersons() {
        return prepareResponseBody(crudService.getAllPersons());
    }

    @GetMapping(SONGS)
    public ResponseEntity<ResBody> getAllSongs() {
        return prepareResponseBody(crudService.getAllSongs());
    }

    @PatchMapping(PERSON)
    public ResponseEntity<ResBody> updatePerson(UpdatePerson updatePerson) {
        return prepareResponseBody(crudService.updatePerson(updatePerson.getPersonIds(), updatePerson.getSongIds()));
    }

    @PatchMapping(SONG)
    public ResponseEntity<ResBody> updateSong(UpdatePerson updatePerson) {
        return prepareResponseBody(crudService.updateSong(updatePerson.getSongIds(), updatePerson.getPersonIds()));
    }

    @DeleteMapping(PERSON)
    public ResponseEntity<ResBody> deletePerson(DeletePerson deletePersonDto) {
        return prepareResponseBody(crudService.deletePerson(deletePersonDto.getPersonIds()));
    }

    @DeleteMapping(SONG)
    public ResponseEntity<ResBody> deleteSong(List<Long> songIds) {
        return prepareResponseBody(crudService.deleteSong(songIds));
    }
}
