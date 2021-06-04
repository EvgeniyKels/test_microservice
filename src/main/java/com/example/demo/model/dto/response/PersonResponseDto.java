package com.example.demo.model.dto.response;

import com.example.demo.model.dto.request.SongDto;
import com.example.demo.model.entity.PersonEntity;
import com.example.demo.model.entity.SongEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class PersonResponseDto {
    private Long personId;
    private String personName;
    private List<SongDto> songCollection;

    public PersonResponseDto(PersonEntity savedPerson, Set<SongEntity> songSet) {
        this.personId = savedPerson.getPersonId();
        this.personName = savedPerson.getPersonName();
        if (songSet != null && !songSet.isEmpty()) {
            this.songCollection = songSet.stream().map(
                    x -> new SongDto(x.getSongId(), x.getSongName())).
                    collect(Collectors.toUnmodifiableList());
        }
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
