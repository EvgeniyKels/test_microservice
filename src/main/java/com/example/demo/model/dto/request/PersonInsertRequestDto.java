package com.example.demo.model.dto.request;

import com.example.demo.config.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public final class PersonInsertRequestDto {
    @NotNull(message = ValidationMessages.PERSON_CANT_BE_NULL)
    @JsonProperty("person")
    private final PersonDto personDto;
    @NotNull(message = ValidationMessages.SONG_LIST_IDS_CANT_BE_NULL)
    @JsonProperty("song_ids")
    private final List<Long> songIds;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PersonInsertRequestDto(
            @NotNull(message = ValidationMessages.PERSON_CANT_BE_NULL) PersonDto personDto,
            @NotNull(message = ValidationMessages.SONG_LIST_IDS_CANT_BE_NULL) List<Long> songIds) {
        this.personDto = personDto;
        this.songIds = Collections.unmodifiableList(songIds);
    }

    public PersonDto getPersonDto() {
        return personDto;
    }

    public List<Long> getSongIds() {
        return songIds;
    }
}
