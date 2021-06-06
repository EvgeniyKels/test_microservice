package com.example.demo.model.dto.request;

import com.example.demo.config.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
//TODO inheritance in dto
public class PersonInsertRequestDto {
    @NotNull(message = ValidationMessages.PERSON_CANT_BE_NULL)
    @JsonProperty("person")
    private PersonDto personDto;
    @NotNull(message = ValidationMessages.SONG_LIST_IDS_CANT_BE_NULL)
    @JsonProperty("song_ids")
    private List<Long> songIds;
}
