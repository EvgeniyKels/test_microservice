package com.example.demo.model.dto.response;

import com.example.demo.model.dto.request.SongDto;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.entity.SongEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class PersonResponseDto {
    @JsonProperty("person_id")
    private final Long personId;
    @JsonProperty("person_name")
    private final String personName;
    @JsonProperty("songs")
    private final List<SongDto> songCollection;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PersonResponseDto(PersonEntity savedPerson, Set<SongEntity> songSet) {
        this.personId = savedPerson.getPersonId();
        this.personName = savedPerson.getPersonName();
        if (songSet != null && !songSet.isEmpty()) {
            this.songCollection = songSet.stream().map(
                    x -> new SongDto(x.getSongId(), x.getSongName())).
                    collect(Collectors.toUnmodifiableList());
        } else {
            this.songCollection = Collections.emptyList();
        }
    }

    public Long getPersonId() {
        return personId;
    }

    public String getPersonName() {
        return personName;
    }

    public List<SongDto> getSongCollection() {
        return Collections.unmodifiableList(songCollection);
    }

    @Override
    public String toString() {
        return "PersonResponseDto{" +
                "personId=" + personId +
                ", personName='" + personName + '\'' +
                ", songCollection=" + songCollection +
                '}';
    }
}
