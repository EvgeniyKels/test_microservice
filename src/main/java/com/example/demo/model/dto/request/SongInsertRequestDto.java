package com.example.demo.model.dto.request;

import com.example.demo.config.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public final class SongInsertRequestDto {
    @NotNull(message = ValidationMessages.SONG_CANT_BE_NULL)
    @JsonProperty("song")
    private final SongDto songDto;
    @NotNull(message = ValidationMessages.PERSON_LIST_IDS_CANT_BE_NULL)
    @JsonProperty("person_ids")
    private final List<Long> personIds;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SongInsertRequestDto(
            @NotNull(message = ValidationMessages.SONG_CANT_BE_NULL) SongDto songDto,
            @NotNull(message = ValidationMessages.PERSON_LIST_IDS_CANT_BE_NULL) List<Long> personIds) {
        this.songDto = songDto;
        this.personIds = Collections.unmodifiableList(personIds);
    }

    public SongDto getSongDto() {
        return songDto;
    }

    public List<Long> getPersonIds() {
        return personIds;
    }
}
