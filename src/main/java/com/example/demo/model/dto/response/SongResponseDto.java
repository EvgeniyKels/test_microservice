package com.example.demo.model.dto.response;

import com.example.demo.model.dto.request.PersonDto;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.entity.SongEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class SongResponseDto {
    private Long songId;
    private String songName;
    private List<PersonDto> personCollection;

    public SongResponseDto(SongEntity savedSong, Set<PersonEntity> personSet) {
        this.songId = savedSong.getSongId();
        this.songName = savedSong.getSongName();
        if (personSet != null && !personSet.isEmpty()) {
            this.personCollection = personSet.stream().map(
                    x -> new PersonDto(x.getPersonId(), x.getPersonName())).
                    collect(Collectors.toUnmodifiableList());
        }
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