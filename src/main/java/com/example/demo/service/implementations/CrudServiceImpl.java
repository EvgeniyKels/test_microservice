package com.example.demo.service.implementations;

import com.example.demo.config.constants.ServiceMessages;
import com.example.demo.model.dto.request.PersonInsertRequestDto;
import com.example.demo.model.dto.request.SongInsertRequestDto;
import com.example.demo.model.dto.response.PersonResponseDto;
import com.example.demo.model.dto.response.SongResponseDto;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.entity.SongEntity;
import com.example.demo.model.repo.IPersonRepo;
import com.example.demo.model.repo.ISongRepo;
import com.example.demo.service.interfaces.ICrudService;
import com.example.demo.util.EntityToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CrudServiceImpl implements ICrudService {

    private final IPersonRepo personRepo;
    private final ISongRepo songRepo;

    @Autowired
    public CrudServiceImpl(IPersonRepo personRepo, ISongRepo songRepo) {
        this.personRepo = personRepo;
        this.songRepo = songRepo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, PersonResponseDto> createPerson(PersonInsertRequestDto personReqDto) {
        Objects.requireNonNull(personReqDto, ServiceMessages.NULL_ON_PERSON_INSERT.getServiceMessage());
        if (personReqDto.getPersonDto().getPersonId() != null) {
            return EntityToDtoMapper.getResultMap(ServiceMessages.WRONG_FORMAT_OF_THE_INPUT_DTO.getServiceMessage(), null);
        }
        String personName = personReqDto.getPersonDto().getPersonName();
        if(personRepo.findByPersonName(personName).isEmpty()) {
            List<Long> songIds = personReqDto.getSongIds();
            var person = new PersonEntity(personName);
            if(songIds != null) {
                List<SongEntity> songEntities = songRepo.findAllBySongIdIn(songIds);
                if(!songIds.isEmpty() && (songEntities.size() == songIds.size())) {
                    for (SongEntity song: songEntities) {
                        song.getPersonSet().add(person);
                        person.getSongSet().add(song);
                    }
                }
            }
            var savedPerson = personRepo.save(person);
            return EntityToDtoMapper.getResultMap(ServiceMessages.INSERTED.getServiceMessage(), new PersonResponseDto(savedPerson, savedPerson.getSongSet()));
        } else {
            return EntityToDtoMapper.getResultMap(ServiceMessages.PERSON_WITH_PROVIDED_NAME_EXISTS_IN_DB.getServiceMessage(), null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, SongResponseDto> createSong(SongInsertRequestDto songReqDTO) {
        Objects.requireNonNull(songReqDTO, ServiceMessages.NULL_ON_SONG_INSERT.getServiceMessage());
        if(songReqDTO.getSongDto().getSongId() != null) {
            return EntityToDtoMapper.getResultMap(ServiceMessages.WRONG_FORMAT_OF_THE_INPUT_DTO.getServiceMessage(), null);
        }
        String songName = songReqDTO.getSongDto().getSongName();
        if(songRepo.findBySongName(songName).isEmpty()) {
            List<Long> personIds = songReqDTO.getPersonIds();
            var song = new SongEntity(songName);
            if (personIds != null) {
                List<PersonEntity> personEntities = personRepo.findAllByPersonIdIn(personIds);
                if(!personIds.isEmpty() && (personEntities.size() == personIds.size())) {
                    for (PersonEntity person: personEntities) {
                        person.getSongSet().add(song);
                        song.getPersonSet().add(person);
                    }
                }
            }
            var savedSong = songRepo.save(song);
            return EntityToDtoMapper.getResultMap(ServiceMessages.INSERTED.getServiceMessage(), new SongResponseDto(savedSong, savedSong.getPersonSet()));
        } else {
            return EntityToDtoMapper.getResultMap(ServiceMessages.SONG_WITH_PROVIDED_NAME_EXISTS_IN_DB.getServiceMessage(), null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, List<PersonResponseDto>> getPersonsBySong(List<Long> songIds) {
        Objects.requireNonNull(songIds, ServiceMessages.SONG_LIST_CANT_BE_NULL.getServiceMessage());
        return EntityToDtoMapper.getResultMap(
                ServiceMessages.RESULT.getServiceMessage(),
                EntityToDtoMapper.mapPersonEntityListToPersonDtoList(
                        personRepo.findAllDistinctBySongSet_SongIdIn(songIds)
                ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, List<SongResponseDto>> getSongsByPerson(List<Long> personIds) {
        Objects.requireNonNull(personIds, ServiceMessages.PERSON_LIST_CANT_BE_NULL.getServiceMessage());
        return EntityToDtoMapper.getResultMap(
                ServiceMessages.RESULT.getServiceMessage(),
                EntityToDtoMapper.mapSongEntityListToSongDtoList(
                        songRepo.findAllByPersonSet_PersonIdIn(personIds)
                ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, List<PersonResponseDto>> getAllPersons() {
        return EntityToDtoMapper.getResultMap(
                ServiceMessages.RESULT.getServiceMessage(),
                EntityToDtoMapper.mapPersonEntityListToPersonDtoList(
                        personRepo.findAll()
                ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, List<SongResponseDto>> getAllSongs() {
        return EntityToDtoMapper.getResultMap(
                ServiceMessages.RESULT.getServiceMessage(),
                EntityToDtoMapper.mapSongEntityListToSongDtoList(
                        songRepo.findAll()
                ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, List<PersonResponseDto>> updatePerson(List<Integer> personIds, List<Integer> songIds) {
        //TODO
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, List<SongResponseDto>> updateSong(List<Integer> songIds, List<Integer> personIds) {
        //TODO
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Long> deletePerson(List<Long> personIds) {
        Objects.requireNonNull(personIds, ServiceMessages.PERSON_LIST_CANT_BE_NULL.getServiceMessage());
        if (personIds.size() == personRepo.countByPersonIdIn(personIds)) {
            personRepo.deleteAllById(personIds); //TODO check relations
            return EntityToDtoMapper.getResultMap(
                    ServiceMessages.DELETED.getServiceMessage(),
                    (long) personIds.size()
            );
        }
        return EntityToDtoMapper.getResultMap(ServiceMessages.NOT_DELETED.getServiceMessage(), null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Long> deleteSong(List<Long> songIds) {
        Objects.requireNonNull(songIds, ServiceMessages.SONG_LIST_CANT_BE_NULL.getServiceMessage());
        if (songIds.size() == songRepo.countBySongIdIn(songIds)) {
            songRepo.deleteAllById(songIds); //TODO check relations
            return EntityToDtoMapper.getResultMap(
                    ServiceMessages.DELETED.getServiceMessage(),
                    (long) songIds.size()
            );
        }
        return EntityToDtoMapper.getResultMap(ServiceMessages.NOT_DELETED.getServiceMessage(), null);
    }
}
