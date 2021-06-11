package com.example.demo.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class UpdateEntity {
    @JsonProperty("person_ids")
    private final List<Integer> personIds;
    @JsonProperty("song_ids")
    private final List<Integer> songIds;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UpdateEntity(List<Integer> personIds, List<Integer> songIds) {
        this.personIds = Collections.unmodifiableList(personIds);
        this.songIds = Collections.unmodifiableList(songIds);
    }

    public List<Integer> getPersonIds() {
        return personIds;
    }

    public List<Integer> getSongIds() {
        return songIds;
    }
}
