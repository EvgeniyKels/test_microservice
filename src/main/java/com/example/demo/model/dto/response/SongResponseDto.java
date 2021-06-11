package com.example.demo.model.dto.response;

import com.example.demo.model.dto.request.PersonDto;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.entity.SongEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class SongResponseDto {
    @JsonProperty("song_id")
    private final Long songId;
    @JsonProperty("song_name")
    private final String songName;
    @JsonProperty("people")
    private final List<PersonDto> personCollection;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SongResponseDto(SongEntity savedSong, Set<PersonEntity> personSet) {
        this.songId = savedSong.getSongId();
        this.songName = savedSong.getSongName();
        if (personSet != null && !personSet.isEmpty()) {
            this.personCollection = personSet.stream().map(
                    x -> new PersonDto(x.getPersonId(), x.getPersonName())).
                    collect(Collectors.toUnmodifiableList());
        } else {
            this.personCollection = Collections.emptyList();
        }
    }

    public Long getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }

    public List<PersonDto> getPersonCollection() {
        return Collections.unmodifiableList(personCollection);
    }

    @Override
    public String toString() {
        return "SongResponseDto{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", personCollection=" + personCollection +
                '}';
    }
}