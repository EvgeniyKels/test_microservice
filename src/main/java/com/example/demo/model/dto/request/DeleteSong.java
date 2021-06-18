package com.example.demo.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public final class DeleteSong {
    @JsonProperty("song_id")
    private final List<Long> songIds;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DeleteSong(List<Long> songIds) {
        this.songIds = Collections.unmodifiableList(songIds);
    }

    public List<Long> getSongIds() {
        return songIds;
    }
}