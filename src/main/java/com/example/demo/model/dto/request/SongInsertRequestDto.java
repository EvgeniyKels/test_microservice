package com.example.demo.model.dto.request;

import com.example.demo.config.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SongInsertRequestDto {
    @NotNull(message = ValidationMessages.SONG_CANT_BE_NULL)
    @JsonProperty("song")
    private SongDto songDto;
    @NotNull(message = ValidationMessages.PERSON_LIST_IDS_CANT_BE_NULL)
    @JsonProperty("person_ids")
    private List<Long> personIds;
}
